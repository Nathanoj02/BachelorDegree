// Generated from C:/Users/Jonathan/Desktop/Scuola/.data/UniVR/3o anno/Linguaggi/Lab/Progetto/src/Imp.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ImpParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ImpVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ImpParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(ImpParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#fun}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFun(ImpParser.FunContext ctx);
	/**
	 * Visit a parse tree produced by the {@code print}
	 * labeled alternative in {@link ImpParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint(ImpParser.PrintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code variable}
	 * labeled alternative in {@link ImpParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(ImpParser.VariableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code readInt}
	 * labeled alternative in {@link ImpParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReadInt(ImpParser.ReadIntContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assign}
	 * labeled alternative in {@link ImpParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign(ImpParser.AssignContext ctx);
	/**
	 * Visit a parse tree produced by the {@code if}
	 * labeled alternative in {@link ImpParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf(ImpParser.IfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code while}
	 * labeled alternative in {@link ImpParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile(ImpParser.WhileContext ctx);
	/**
	 * Visit a parse tree produced by the {@code call}
	 * labeled alternative in {@link ImpParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCall(ImpParser.CallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code empty}
	 * labeled alternative in {@link ImpParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmpty(ImpParser.EmptyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code int}
	 * labeled alternative in {@link ImpParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInt(ImpParser.IntContext ctx);
	/**
	 * Visit a parse tree produced by the {@code macro0}
	 * labeled alternative in {@link ImpParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMacro0(ImpParser.Macro0Context ctx);
	/**
	 * Visit a parse tree produced by the {@code macro1}
	 * labeled alternative in {@link ImpParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMacro1(ImpParser.Macro1Context ctx);
	/**
	 * Visit a parse tree produced by the {@code string}
	 * labeled alternative in {@link ImpParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(ImpParser.StringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code id}
	 * labeled alternative in {@link ImpParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId(ImpParser.IdContext ctx);
}