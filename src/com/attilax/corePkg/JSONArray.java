/**
 * @author attilax 老哇的爪子
	@since  2014-5-19 上午11:27:54$
 */
package com.attilax.corePkg;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.ecs.xhtml.object;

/**
 * @author  attilax 老哇的爪子
 *@since  2014-5-19 上午11:27:54$
 */
public class JSONArray  implements List{
	//  attilax 老哇的爪子 上午11:27:54   2014-5-19   
	
	net.sf.json.JSONArray jo=new net.sf.json.JSONArray();

	public boolean add(Object jsonObj) {
		// attilax 老哇的爪子  上午11:29:12   2014-5-19 
		JSONObject  jso=(JSONObject) jsonObj;
		
	return	jo.add(jso.jo);
	}
	
	public net.sf.json.JSONArray $() {
		// attilax 老哇的爪子  上午11:29:12   2014-5-19 
		return jo;
		
	}

	/* (non-Javadoc)
	 * @see java.util.List#add(int, java.lang.Object)
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-26 下午02:19:15$
	 */
	@Override
	public void add(int arg0, Object arg1) {
		// attilax 老哇的爪子  下午02:19:15   2014-5-26 
		
	}

	/* (non-Javadoc)
	 * @see java.util.List#addAll(java.util.Collection)
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-26 下午02:19:15$
	 */
	@Override
	public boolean addAll(Collection arg0) {
		// attilax 老哇的爪子  下午02:19:15   2014-5-26 
		return false;
	}

	/* (non-Javadoc)
	 * @see java.util.List#addAll(int, java.util.Collection)
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-26 下午02:19:15$
	 */
	@Override
	public boolean addAll(int arg0, Collection arg1) {
		// attilax 老哇的爪子  下午02:19:15   2014-5-26 
		return false;
	}

	/* (non-Javadoc)
	 * @see java.util.List#clear()
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-26 下午02:19:15$
	 */
	@Override
	public void clear() {
		// attilax 老哇的爪子  下午02:19:15   2014-5-26 
		
	}

	/* (non-Javadoc)
	 * @see java.util.List#contains(java.lang.Object)
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-26 下午02:19:15$
	 */
	@Override
	public boolean contains(Object arg0) {
		// attilax 老哇的爪子  下午02:19:15   2014-5-26 
		return false;
	}

	/* (non-Javadoc)
	 * @see java.util.List#containsAll(java.util.Collection)
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-26 下午02:19:15$
	 */
	@Override
	public boolean containsAll(Collection arg0) {
		// attilax 老哇的爪子  下午02:19:15   2014-5-26 
		return false;
	}

	/* (non-Javadoc)
	 * @see java.util.List#get(int)
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-26 下午02:19:15$
	 */
	@Override
	public Object get(int arg0) {
		// attilax 老哇的爪子  下午02:19:15   2014-5-26 
		return null;
	}

	/* (non-Javadoc)
	 * @see java.util.List#indexOf(java.lang.Object)
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-26 下午02:19:15$
	 */
	@Override
	public int indexOf(Object arg0) {
		// attilax 老哇的爪子  下午02:19:15   2014-5-26 
		return 0;
	}

	/* (non-Javadoc)
	 * @see java.util.List#isEmpty()
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-26 下午02:19:15$
	 */
	@Override
	public boolean isEmpty() {
		// attilax 老哇的爪子  下午02:19:15   2014-5-26 
		return false;
	}

	/* (non-Javadoc)
	 * @see java.util.List#iterator()
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-26 下午02:19:15$
	 */
	@Override
	public Iterator iterator() {
		// attilax 老哇的爪子  下午02:19:15   2014-5-26 
		return null;
	}

	/* (non-Javadoc)
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-26 下午02:19:15$
	 */
	@Override
	public int lastIndexOf(Object arg0) {
		// attilax 老哇的爪子  下午02:19:15   2014-5-26 
		return 0;
	}

	/* (non-Javadoc)
	 * @see java.util.List#listIterator()
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-26 下午02:19:15$
	 */
	@Override
	public ListIterator listIterator() {
		// attilax 老哇的爪子  下午02:19:15   2014-5-26 
		return null;
	}

	/* (non-Javadoc)
	 * @see java.util.List#listIterator(int)
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-26 下午02:19:15$
	 */
	@Override
	public ListIterator listIterator(int arg0) {
		// attilax 老哇的爪子  下午02:19:15   2014-5-26 
		return null;
	}

	/* (non-Javadoc)
	 * @see java.util.List#remove(java.lang.Object)
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-26 下午02:19:15$
	 */
	@Override
	public boolean remove(Object arg0) {
		// attilax 老哇的爪子  下午02:19:15   2014-5-26 
		return false;
	}

	/* (non-Javadoc)
	 * @see java.util.List#remove(int)
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-26 下午02:19:15$
	 */
	@Override
	public Object remove(int arg0) {
		// attilax 老哇的爪子  下午02:19:15   2014-5-26 
		return null;
	}

	/* (non-Javadoc)
	 * @see java.util.List#removeAll(java.util.Collection)
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-26 下午02:19:15$
	 */
	@Override
	public boolean removeAll(Collection arg0) {
		// attilax 老哇的爪子  下午02:19:15   2014-5-26 
		return false;
	}

	/* (non-Javadoc)
	 * @see java.util.List#retainAll(java.util.Collection)
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-26 下午02:19:15$
	 */
	@Override
	public boolean retainAll(Collection arg0) {
		// attilax 老哇的爪子  下午02:19:15   2014-5-26 
		return false;
	}

	/* (non-Javadoc)
	 * @see java.util.List#set(int, java.lang.Object)
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-26 下午02:19:15$
	 */
	@Override
	public Object set(int arg0, Object arg1) {
		// attilax 老哇的爪子  下午02:19:15   2014-5-26 
		return null;
	}

	/* (non-Javadoc)
	 * @see java.util.List#size()
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-26 下午02:19:15$
	 */
	@Override
	public int size() {
		// attilax 老哇的爪子  下午02:19:15   2014-5-26 
		return 0;
	}

	/* (non-Javadoc)
	 * @see java.util.List#subList(int, int)
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-26 下午02:19:15$
	 */
	@Override
	public List subList(int arg0, int arg1) {
		// attilax 老哇的爪子  下午02:19:15   2014-5-26 
		return null;
	}

	/* (non-Javadoc)
	 * @see java.util.List#toArray()
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-26 下午02:19:15$
	 */
	@Override
	public Object[] toArray() {
		// attilax 老哇的爪子  下午02:19:15   2014-5-26 
		return null;
	}

	/* (non-Javadoc)
	 * @see java.util.List#toArray(T[])
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-26 下午02:19:15$
	 */
	@Override
	public Object[] toArray(Object[] arg0) {
		// attilax 老哇的爪子  下午02:19:15   2014-5-26 
		return null;
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-5-26 下午02:25:28$
	
	 * @param i
	 * @return
	 */
	public String toString(int i) {
		// attilax 老哇的爪子  下午02:25:28   2014-5-26 
		return this.jo.toString(i);
	}
}

//  attilax 老哇的爪子