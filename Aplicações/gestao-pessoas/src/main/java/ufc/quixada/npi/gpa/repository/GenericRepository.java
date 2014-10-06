package ufc.quixada.npi.gpa.repository;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;



public interface GenericRepository<T> {

	  void setEntityManager(EntityManager em);

	  void save(T entity);
	
	  void update(T entity);

	  void delete(T entity);

	  T find(Class<T> entityClass, Object id);

	  List<T> find(Class<T> entityClass);

	  List<T> find(Class<T> entityClass, int firstResult, int maxResults);

	  List<T> find(String queryName,
			Map<String, Object> namedParams);

	/**
	 * 
	 * @param type
	 * @param query
	 * @param namedParams
	 * @return
	 */
	 List<T> find(QueryType type, String query,
			Map<String, Object> namedParams);

	 List<T> find(String queryName,
			Map<String, Object> namedParams, int firstResult, int maxResults);

	/**
	 * 
	 * @param type
	 * @param query
	 * @param namedParams
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	 List<T> find(QueryType type, String query,
			Map<String, Object> namedParams, int firstResult, int maxResults);

	/**
	 * 
	 * @param query
	 * @param namedParams
	 * @return
	 */
	 T findFirst(String query, Map<String, Object> namedParams);

	/**
	 * 
	 * @param query
	 * @param namedParams
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	 T findFirst(String query, Map<String, Object> namedParams,
			int firstResult, int maxResults);

	/**
	 * 
	 * @param type
	 * @param query
	 * @param namedParams
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	 T findFirst(QueryType type, String query,
			Map<String, Object> namedParams, int firstResult, int maxResults);

	

}