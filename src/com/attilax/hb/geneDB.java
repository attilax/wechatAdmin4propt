/**
 * 
 */
package com.attilax.hb;

import org.hibernate.Session;

import com.attilax.util.HibernateSessionFactory;

/**
 * @author ASIMO
 *
 */
public class geneDB {

	/**
	@author attilax 老哇的爪子
	@since   p2k k_l_v
	 
	 */
	public static void main(String[] args) {
		Session s=	HibernateSessionFactory4geneDb.getSession();
		s.merge(new Object());
		System.out.println("---");

	}

}
