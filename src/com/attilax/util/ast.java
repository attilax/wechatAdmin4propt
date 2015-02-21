///**
// * @author attilax 老哇的爪子
//	@since  2014-4-18 下午05:38:10$
// */
//package com.attilax.util;
//
//import org.eclipse.jdt.core.dom.AST;
//import org.eclipse.jdt.core.dom.ASTParser;
//import org.eclipse.jdt.core.dom.CompilationUnit;
//
// 
//
///**
// * @author attilax
// *
// */
//public class ast {
//
//	/**
//	@author attilax 老哇的爪子
//		@since  2014-4-18 下午05:38:11$
//	
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		// 下午05:38:11   2014-4-18 
//
//	}
//	
//	private static CompilationUnit parse(String content) {
//        ASTParser parser = ASTParser.newParser(AST.JLS3);
//
//        parser.setKind(ASTParser.K_COMPILATION_UNIT);     //to parse compilation unit 
//        parser.setSource(content.toCharArray());          //content is a string which stores the java source 
//        parser.setResolveBindings(true); 
//        CompilationUnit result = (CompilationUnit) parser.createAST(null);
//        return result;
//    }
//
//}
