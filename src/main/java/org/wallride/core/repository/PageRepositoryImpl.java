package org.wallride.core.repository;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.QueryParser.Operator;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.util.Version;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.util.StringUtils;
import org.wallride.core.domain.Page;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PageRepositoryImpl implements PageRepositoryCustom {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Long> findByFullTextSearchTerm(PageFullTextSearchTerm term) {
		FullTextEntityManager fullTextEntityManager =  Search.getFullTextEntityManager(entityManager);
		QueryBuilder qb = fullTextEntityManager.getSearchFactory()
				.buildQueryBuilder()
				.forEntity(Page.class)
				.get();
		
		@SuppressWarnings("rawtypes")
		BooleanJunction<BooleanJunction> junction = qb.bool();

		if (StringUtils.hasText(term.getKeyword())) {
			Analyzer analyzer = fullTextEntityManager.getSearchFactory().getAnalyzer("synonyms");
			String[] fields = new String[] {
					"title", "body",
			};
			MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_36, fields, analyzer);
			parser.setDefaultOperator(Operator.AND);
			Query query = null;
			try {
				query = parser.parse(term.getKeyword());
			}
			catch (ParseException e1) {
				try {
					query = parser.parse(QueryParser.escape(term.getKeyword()));
				}
				catch (ParseException e2) {
					throw new RuntimeException(e2);
				}
			}
			junction.must(query);
		}
		if (StringUtils.hasText(term.getLanguage())) {
			junction.must(qb.keyword().onField("language").matching(term.getLanguage()).createQuery());
		}
		
		Query searchQuery = junction.createQuery();
		
		Sort sort = new Sort(new SortField("lft", SortField.INT));
		
		javax.persistence.Query persistenceQuery = fullTextEntityManager
				.createFullTextQuery(searchQuery, Page.class)
				.setProjection("id")
				.setSort(sort);
		@SuppressWarnings("unchecked")
		List<Object[]> results = persistenceQuery.getResultList();
		
		Set<Long> ids = new LinkedHashSet<Long>();
		for (Object[] object : results) {
			ids.add((Long) object[0]);
		}
		
		return new ArrayList<>(ids);
	}

}
