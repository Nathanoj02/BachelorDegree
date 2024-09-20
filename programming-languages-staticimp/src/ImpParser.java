// Generated from C:/Users/Jonathan/Desktop/Scuola/.data/UniVR/3o anno/Linguaggi/Lab/Progetto/src/Imp.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class ImpParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		START_MAIN=1, END_MAIN=2, PRINT=3, DECLARE=4, ASSIGN=5, READ_INT=6, START_EXP=7, 
		FIST_OP=8, END_EXP=9, IF=10, ELSE=11, END_IF=12, WHILE=13, END_WHILE=14, 
		START_FUN=15, END_FUN=16, FUN_ARG=17, NON_VOID=18, RETURN=19, CALL=20, 
		FUN_ASGN=21, PLUS=22, MINUS=23, MUL=24, DIV=25, MOD=26, EQ=27, GT=28, 
		OR=29, AND=30, INT=31, MACRO_0=32, MACRO_1=33, STRING=34, ID=35, ENDL=36, 
		WS=37;
	public static final int
		RULE_prog = 0, RULE_fun = 1, RULE_com = 2, RULE_exp = 3;
	private static String[] makeRuleNames() {
		return new String[] {
			"prog", "fun", "com", "exp"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'IT'S SHOWTIME'", "'YOU HAVE BEEN TERMINATED'", "'TALK TO THE HAND'", 
			"'HEY CHRISTMAS TREE'", "'YOU SET US UP'", "'I WANT TO ASK YOU A BUNCH OF QUESTIONS AND I WANT TO HAVE THEM ANSWERED IMMEDIATELY'", 
			"'GET TO THE CHOPPER'", "'HERE IS MY INVITATION'", "'ENOUGH TALK'", "'BECAUSE I'M GOING TO SAY PLEASE'", 
			"'BULLSHIT'", "'YOU HAVE NO RESPECT FOR LOGIC'", "'STICK AROUND'", "'CHILL'", 
			"'LISTEN TO ME VERY CAREFULLY'", "'HASTA LA VISTA, BABY'", "'I NEED YOUR CLOTHES YOUR BOOTS AND YOUR MOTORCYCLE'", 
			"'GIVE THESE PEOPLE AIR'", "'I'LL BE BACK'", "'DO IT NOW'", "'GET YOUR ASS TO MARS'", 
			"'GET UP'", "'GET DOWN'", "'YOU'RE FIRED'", "'HE HAD TO SPLIT'", "'I LET HIM GO'", 
			"'YOU ARE NOT YOU YOU ARE ME'", "'LET OFF SOME STEAM BENNET'", "'CONSIDER THAT A DIVORCE'", 
			"'KNOCK KNOCK'", null, "'@I LIED'", "'@NO PROBLEMO'", null, null, "'\\n'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "START_MAIN", "END_MAIN", "PRINT", "DECLARE", "ASSIGN", "READ_INT", 
			"START_EXP", "FIST_OP", "END_EXP", "IF", "ELSE", "END_IF", "WHILE", "END_WHILE", 
			"START_FUN", "END_FUN", "FUN_ARG", "NON_VOID", "RETURN", "CALL", "FUN_ASGN", 
			"PLUS", "MINUS", "MUL", "DIV", "MOD", "EQ", "GT", "OR", "AND", "INT", 
			"MACRO_0", "MACRO_1", "STRING", "ID", "ENDL", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Imp.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ImpParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgContext extends ParserRuleContext {
		public TerminalNode START_MAIN() { return getToken(ImpParser.START_MAIN, 0); }
		public ComContext com() {
			return getRuleContext(ComContext.class,0);
		}
		public TerminalNode END_MAIN() { return getToken(ImpParser.END_MAIN, 0); }
		public TerminalNode EOF() { return getToken(ImpParser.EOF, 0); }
		public List<FunContext> fun() {
			return getRuleContexts(FunContext.class);
		}
		public FunContext fun(int i) {
			return getRuleContext(FunContext.class,i);
		}
		public List<TerminalNode> ENDL() { return getTokens(ImpParser.ENDL); }
		public TerminalNode ENDL(int i) {
			return getToken(ImpParser.ENDL, i);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ImpVisitor ) return ((ImpVisitor<? extends T>)visitor).visitProg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(16);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==START_FUN) {
				{
				{
				setState(8);
				fun();
				setState(10); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(9);
					match(ENDL);
					}
					}
					setState(12); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==ENDL );
				}
				}
				setState(18);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(19);
			match(START_MAIN);
			setState(21); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(20);
					match(ENDL);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(23); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			setState(25);
			com();
			setState(29);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ENDL) {
				{
				{
				setState(26);
				match(ENDL);
				}
				}
				setState(31);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(32);
			match(END_MAIN);
			setState(48);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==START_FUN || _la==ENDL) {
				{
				{
				setState(36);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ENDL) {
					{
					{
					setState(33);
					match(ENDL);
					}
					}
					setState(38);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(39);
				fun();
				setState(43);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(40);
						match(ENDL);
						}
						} 
					}
					setState(45);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
				}
				}
				}
				setState(50);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(51);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FunContext extends ParserRuleContext {
		public TerminalNode START_FUN() { return getToken(ImpParser.START_FUN, 0); }
		public List<TerminalNode> ID() { return getTokens(ImpParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(ImpParser.ID, i);
		}
		public ComContext com() {
			return getRuleContext(ComContext.class,0);
		}
		public TerminalNode END_FUN() { return getToken(ImpParser.END_FUN, 0); }
		public List<TerminalNode> ENDL() { return getTokens(ImpParser.ENDL); }
		public TerminalNode ENDL(int i) {
			return getToken(ImpParser.ENDL, i);
		}
		public List<TerminalNode> FUN_ARG() { return getTokens(ImpParser.FUN_ARG); }
		public TerminalNode FUN_ARG(int i) {
			return getToken(ImpParser.FUN_ARG, i);
		}
		public TerminalNode NON_VOID() { return getToken(ImpParser.NON_VOID, 0); }
		public TerminalNode RETURN() { return getToken(ImpParser.RETURN, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public FunContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fun; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ImpVisitor ) return ((ImpVisitor<? extends T>)visitor).visitFun(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunContext fun() throws RecognitionException {
		FunContext _localctx = new FunContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_fun);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(53);
			match(START_FUN);
			setState(54);
			match(ID);
			setState(56); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(55);
				match(ENDL);
				}
				}
				setState(58); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==ENDL );
			setState(69);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FUN_ARG) {
				{
				{
				setState(60);
				match(FUN_ARG);
				setState(61);
				match(ID);
				setState(63); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(62);
					match(ENDL);
					}
					}
					setState(65); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==ENDL );
				}
				}
				setState(71);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(78);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NON_VOID) {
				{
				setState(72);
				match(NON_VOID);
				setState(74); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(73);
					match(ENDL);
					}
					}
					setState(76); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==ENDL );
				}
			}

			setState(80);
			com();
			setState(88);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==RETURN) {
				{
				setState(81);
				match(RETURN);
				setState(82);
				exp();
				setState(84); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(83);
					match(ENDL);
					}
					}
					setState(86); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==ENDL );
				}
			}

			setState(90);
			match(END_FUN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ComContext extends ParserRuleContext {
		public ComContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_com; }
	 
		public ComContext() { }
		public void copyFrom(ComContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CallContext extends ComContext {
		public TerminalNode CALL() { return getToken(ImpParser.CALL, 0); }
		public List<TerminalNode> ID() { return getTokens(ImpParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(ImpParser.ID, i);
		}
		public ComContext com() {
			return getRuleContext(ComContext.class,0);
		}
		public TerminalNode FUN_ASGN() { return getToken(ImpParser.FUN_ASGN, 0); }
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public List<TerminalNode> ENDL() { return getTokens(ImpParser.ENDL); }
		public TerminalNode ENDL(int i) {
			return getToken(ImpParser.ENDL, i);
		}
		public CallContext(ComContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ImpVisitor ) return ((ImpVisitor<? extends T>)visitor).visitCall(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PrintContext extends ComContext {
		public TerminalNode PRINT() { return getToken(ImpParser.PRINT, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public ComContext com() {
			return getRuleContext(ComContext.class,0);
		}
		public List<TerminalNode> ENDL() { return getTokens(ImpParser.ENDL); }
		public TerminalNode ENDL(int i) {
			return getToken(ImpParser.ENDL, i);
		}
		public PrintContext(ComContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ImpVisitor ) return ((ImpVisitor<? extends T>)visitor).visitPrint(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class VariableContext extends ComContext {
		public TerminalNode DECLARE() { return getToken(ImpParser.DECLARE, 0); }
		public TerminalNode ID() { return getToken(ImpParser.ID, 0); }
		public TerminalNode ASSIGN() { return getToken(ImpParser.ASSIGN, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public ComContext com() {
			return getRuleContext(ComContext.class,0);
		}
		public List<TerminalNode> ENDL() { return getTokens(ImpParser.ENDL); }
		public TerminalNode ENDL(int i) {
			return getToken(ImpParser.ENDL, i);
		}
		public VariableContext(ComContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ImpVisitor ) return ((ImpVisitor<? extends T>)visitor).visitVariable(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ReadIntContext extends ComContext {
		public TerminalNode READ_INT() { return getToken(ImpParser.READ_INT, 0); }
		public TerminalNode ID() { return getToken(ImpParser.ID, 0); }
		public ComContext com() {
			return getRuleContext(ComContext.class,0);
		}
		public List<TerminalNode> ENDL() { return getTokens(ImpParser.ENDL); }
		public TerminalNode ENDL(int i) {
			return getToken(ImpParser.ENDL, i);
		}
		public ReadIntContext(ComContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ImpVisitor ) return ((ImpVisitor<? extends T>)visitor).visitReadInt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class WhileContext extends ComContext {
		public TerminalNode WHILE() { return getToken(ImpParser.WHILE, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public List<ComContext> com() {
			return getRuleContexts(ComContext.class);
		}
		public ComContext com(int i) {
			return getRuleContext(ComContext.class,i);
		}
		public TerminalNode END_WHILE() { return getToken(ImpParser.END_WHILE, 0); }
		public List<TerminalNode> ENDL() { return getTokens(ImpParser.ENDL); }
		public TerminalNode ENDL(int i) {
			return getToken(ImpParser.ENDL, i);
		}
		public WhileContext(ComContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ImpVisitor ) return ((ImpVisitor<? extends T>)visitor).visitWhile(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IfContext extends ComContext {
		public TerminalNode IF() { return getToken(ImpParser.IF, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public List<ComContext> com() {
			return getRuleContexts(ComContext.class);
		}
		public ComContext com(int i) {
			return getRuleContext(ComContext.class,i);
		}
		public TerminalNode END_IF() { return getToken(ImpParser.END_IF, 0); }
		public List<TerminalNode> ENDL() { return getTokens(ImpParser.ENDL); }
		public TerminalNode ENDL(int i) {
			return getToken(ImpParser.ENDL, i);
		}
		public TerminalNode ELSE() { return getToken(ImpParser.ELSE, 0); }
		public IfContext(ComContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ImpVisitor ) return ((ImpVisitor<? extends T>)visitor).visitIf(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AssignContext extends ComContext {
		public Token op;
		public TerminalNode START_EXP() { return getToken(ImpParser.START_EXP, 0); }
		public TerminalNode ID() { return getToken(ImpParser.ID, 0); }
		public TerminalNode FIST_OP() { return getToken(ImpParser.FIST_OP, 0); }
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TerminalNode END_EXP() { return getToken(ImpParser.END_EXP, 0); }
		public ComContext com() {
			return getRuleContext(ComContext.class,0);
		}
		public List<TerminalNode> ENDL() { return getTokens(ImpParser.ENDL); }
		public TerminalNode ENDL(int i) {
			return getToken(ImpParser.ENDL, i);
		}
		public List<TerminalNode> PLUS() { return getTokens(ImpParser.PLUS); }
		public TerminalNode PLUS(int i) {
			return getToken(ImpParser.PLUS, i);
		}
		public List<TerminalNode> MINUS() { return getTokens(ImpParser.MINUS); }
		public TerminalNode MINUS(int i) {
			return getToken(ImpParser.MINUS, i);
		}
		public List<TerminalNode> MUL() { return getTokens(ImpParser.MUL); }
		public TerminalNode MUL(int i) {
			return getToken(ImpParser.MUL, i);
		}
		public List<TerminalNode> DIV() { return getTokens(ImpParser.DIV); }
		public TerminalNode DIV(int i) {
			return getToken(ImpParser.DIV, i);
		}
		public List<TerminalNode> MOD() { return getTokens(ImpParser.MOD); }
		public TerminalNode MOD(int i) {
			return getToken(ImpParser.MOD, i);
		}
		public List<TerminalNode> EQ() { return getTokens(ImpParser.EQ); }
		public TerminalNode EQ(int i) {
			return getToken(ImpParser.EQ, i);
		}
		public List<TerminalNode> GT() { return getTokens(ImpParser.GT); }
		public TerminalNode GT(int i) {
			return getToken(ImpParser.GT, i);
		}
		public List<TerminalNode> AND() { return getTokens(ImpParser.AND); }
		public TerminalNode AND(int i) {
			return getToken(ImpParser.AND, i);
		}
		public List<TerminalNode> OR() { return getTokens(ImpParser.OR); }
		public TerminalNode OR(int i) {
			return getToken(ImpParser.OR, i);
		}
		public AssignContext(ComContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ImpVisitor ) return ((ImpVisitor<? extends T>)visitor).visitAssign(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EmptyContext extends ComContext {
		public EmptyContext(ComContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ImpVisitor ) return ((ImpVisitor<? extends T>)visitor).visitEmpty(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComContext com() throws RecognitionException {
		ComContext _localctx = new ComContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_com);
		int _la;
		try {
			int _alt;
			setState(242);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PRINT:
				_localctx = new PrintContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(92);
				match(PRINT);
				setState(93);
				exp();
				setState(95); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(94);
						match(ENDL);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(97); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(99);
				com();
				}
				break;
			case DECLARE:
				_localctx = new VariableContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(101);
				match(DECLARE);
				setState(102);
				match(ID);
				setState(104); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(103);
					match(ENDL);
					}
					}
					setState(106); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==ENDL );
				setState(108);
				match(ASSIGN);
				setState(109);
				exp();
				setState(111); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(110);
						match(ENDL);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(113); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(115);
				com();
				}
				break;
			case READ_INT:
				_localctx = new ReadIntContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(117);
				match(READ_INT);
				setState(118);
				match(ID);
				setState(120); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(119);
						match(ENDL);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(122); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(124);
				com();
				}
				break;
			case START_EXP:
				_localctx = new AssignContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(125);
				match(START_EXP);
				setState(126);
				match(ID);
				setState(128); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(127);
					match(ENDL);
					}
					}
					setState(130); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==ENDL );
				setState(132);
				match(FIST_OP);
				setState(133);
				exp();
				setState(135); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(134);
					match(ENDL);
					}
					}
					setState(137); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==ENDL );
				setState(148);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2143289344L) != 0)) {
					{
					{
					setState(139);
					((AssignContext)_localctx).op = _input.LT(1);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 2143289344L) != 0)) ) {
						((AssignContext)_localctx).op = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(140);
					exp();
					setState(142); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(141);
						match(ENDL);
						}
						}
						setState(144); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( _la==ENDL );
					}
					}
					setState(150);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(151);
				match(END_EXP);
				setState(153); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(152);
						match(ENDL);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(155); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(157);
				com();
				}
				break;
			case IF:
				_localctx = new IfContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(159);
				match(IF);
				setState(160);
				exp();
				setState(162); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(161);
						match(ENDL);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(164); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(166);
				com();
				setState(170);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ENDL) {
					{
					{
					setState(167);
					match(ENDL);
					}
					}
					setState(172);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(186);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ELSE) {
					{
					setState(173);
					match(ELSE);
					setState(175); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(174);
							match(ENDL);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(177); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(179);
					com();
					setState(183);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==ENDL) {
						{
						{
						setState(180);
						match(ENDL);
						}
						}
						setState(185);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(188);
				match(END_IF);
				setState(190); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(189);
						match(ENDL);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(192); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(194);
				com();
				}
				break;
			case WHILE:
				_localctx = new WhileContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(196);
				match(WHILE);
				setState(197);
				exp();
				setState(199); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(198);
						match(ENDL);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(201); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(203);
				com();
				setState(207);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ENDL) {
					{
					{
					setState(204);
					match(ENDL);
					}
					}
					setState(209);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(210);
				match(END_WHILE);
				setState(212); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(211);
						match(ENDL);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(214); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(216);
				com();
				}
				break;
			case CALL:
			case FUN_ASGN:
				_localctx = new CallContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(225);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==FUN_ASGN) {
					{
					setState(218);
					match(FUN_ASGN);
					setState(219);
					match(ID);
					setState(221); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(220);
						match(ENDL);
						}
						}
						setState(223); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( _la==ENDL );
					}
				}

				setState(227);
				match(CALL);
				setState(228);
				match(ID);
				setState(232);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 66571993088L) != 0)) {
					{
					{
					setState(229);
					exp();
					}
					}
					setState(234);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(236); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(235);
						match(ENDL);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(238); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(240);
				com();
				}
				break;
			case END_MAIN:
			case ELSE:
			case END_IF:
			case END_WHILE:
			case END_FUN:
			case RETURN:
			case ENDL:
				_localctx = new EmptyContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpContext extends ParserRuleContext {
		public ExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp; }
	 
		public ExpContext() { }
		public void copyFrom(ExpContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Macro1Context extends ExpContext {
		public TerminalNode MACRO_1() { return getToken(ImpParser.MACRO_1, 0); }
		public Macro1Context(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ImpVisitor ) return ((ImpVisitor<? extends T>)visitor).visitMacro1(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Macro0Context extends ExpContext {
		public TerminalNode MACRO_0() { return getToken(ImpParser.MACRO_0, 0); }
		public Macro0Context(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ImpVisitor ) return ((ImpVisitor<? extends T>)visitor).visitMacro0(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringContext extends ExpContext {
		public TerminalNode STRING() { return getToken(ImpParser.STRING, 0); }
		public StringContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ImpVisitor ) return ((ImpVisitor<? extends T>)visitor).visitString(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IdContext extends ExpContext {
		public TerminalNode ID() { return getToken(ImpParser.ID, 0); }
		public IdContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ImpVisitor ) return ((ImpVisitor<? extends T>)visitor).visitId(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IntContext extends ExpContext {
		public TerminalNode INT() { return getToken(ImpParser.INT, 0); }
		public IntContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ImpVisitor ) return ((ImpVisitor<? extends T>)visitor).visitInt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpContext exp() throws RecognitionException {
		ExpContext _localctx = new ExpContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_exp);
		try {
			setState(249);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT:
				_localctx = new IntContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(244);
				match(INT);
				}
				break;
			case MACRO_0:
				_localctx = new Macro0Context(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(245);
				match(MACRO_0);
				}
				break;
			case MACRO_1:
				_localctx = new Macro1Context(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(246);
				match(MACRO_1);
				}
				break;
			case STRING:
				_localctx = new StringContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(247);
				match(STRING);
				}
				break;
			case ID:
				_localctx = new IdContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(248);
				match(ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001%\u00fc\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0001\u0000\u0001\u0000\u0004"+
		"\u0000\u000b\b\u0000\u000b\u0000\f\u0000\f\u0005\u0000\u000f\b\u0000\n"+
		"\u0000\f\u0000\u0012\t\u0000\u0001\u0000\u0001\u0000\u0004\u0000\u0016"+
		"\b\u0000\u000b\u0000\f\u0000\u0017\u0001\u0000\u0001\u0000\u0005\u0000"+
		"\u001c\b\u0000\n\u0000\f\u0000\u001f\t\u0000\u0001\u0000\u0001\u0000\u0005"+
		"\u0000#\b\u0000\n\u0000\f\u0000&\t\u0000\u0001\u0000\u0001\u0000\u0005"+
		"\u0000*\b\u0000\n\u0000\f\u0000-\t\u0000\u0005\u0000/\b\u0000\n\u0000"+
		"\f\u00002\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0004\u00019\b\u0001\u000b\u0001\f\u0001:\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0004\u0001@\b\u0001\u000b\u0001\f\u0001A\u0005\u0001D\b"+
		"\u0001\n\u0001\f\u0001G\t\u0001\u0001\u0001\u0001\u0001\u0004\u0001K\b"+
		"\u0001\u000b\u0001\f\u0001L\u0003\u0001O\b\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0004\u0001U\b\u0001\u000b\u0001\f\u0001V\u0003"+
		"\u0001Y\b\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0004\u0002`\b\u0002\u000b\u0002\f\u0002a\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0004\u0002i\b\u0002\u000b\u0002"+
		"\f\u0002j\u0001\u0002\u0001\u0002\u0001\u0002\u0004\u0002p\b\u0002\u000b"+
		"\u0002\f\u0002q\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0004\u0002y\b\u0002\u000b\u0002\f\u0002z\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0004\u0002\u0081\b\u0002\u000b\u0002\f\u0002"+
		"\u0082\u0001\u0002\u0001\u0002\u0001\u0002\u0004\u0002\u0088\b\u0002\u000b"+
		"\u0002\f\u0002\u0089\u0001\u0002\u0001\u0002\u0001\u0002\u0004\u0002\u008f"+
		"\b\u0002\u000b\u0002\f\u0002\u0090\u0005\u0002\u0093\b\u0002\n\u0002\f"+
		"\u0002\u0096\t\u0002\u0001\u0002\u0001\u0002\u0004\u0002\u009a\b\u0002"+
		"\u000b\u0002\f\u0002\u009b\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0004\u0002\u00a3\b\u0002\u000b\u0002\f\u0002\u00a4\u0001"+
		"\u0002\u0001\u0002\u0005\u0002\u00a9\b\u0002\n\u0002\f\u0002\u00ac\t\u0002"+
		"\u0001\u0002\u0001\u0002\u0004\u0002\u00b0\b\u0002\u000b\u0002\f\u0002"+
		"\u00b1\u0001\u0002\u0001\u0002\u0005\u0002\u00b6\b\u0002\n\u0002\f\u0002"+
		"\u00b9\t\u0002\u0003\u0002\u00bb\b\u0002\u0001\u0002\u0001\u0002\u0004"+
		"\u0002\u00bf\b\u0002\u000b\u0002\f\u0002\u00c0\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0004\u0002\u00c8\b\u0002\u000b\u0002"+
		"\f\u0002\u00c9\u0001\u0002\u0001\u0002\u0005\u0002\u00ce\b\u0002\n\u0002"+
		"\f\u0002\u00d1\t\u0002\u0001\u0002\u0001\u0002\u0004\u0002\u00d5\b\u0002"+
		"\u000b\u0002\f\u0002\u00d6\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0004\u0002\u00de\b\u0002\u000b\u0002\f\u0002\u00df\u0003"+
		"\u0002\u00e2\b\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u0002\u00e7"+
		"\b\u0002\n\u0002\f\u0002\u00ea\t\u0002\u0001\u0002\u0004\u0002\u00ed\b"+
		"\u0002\u000b\u0002\f\u0002\u00ee\u0001\u0002\u0001\u0002\u0003\u0002\u00f3"+
		"\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003"+
		"\u0003\u00fa\b\u0003\u0001\u0003\u0000\u0000\u0004\u0000\u0002\u0004\u0006"+
		"\u0000\u0001\u0001\u0000\u0016\u001e\u0126\u0000\u0010\u0001\u0000\u0000"+
		"\u0000\u00025\u0001\u0000\u0000\u0000\u0004\u00f2\u0001\u0000\u0000\u0000"+
		"\u0006\u00f9\u0001\u0000\u0000\u0000\b\n\u0003\u0002\u0001\u0000\t\u000b"+
		"\u0005$\u0000\u0000\n\t\u0001\u0000\u0000\u0000\u000b\f\u0001\u0000\u0000"+
		"\u0000\f\n\u0001\u0000\u0000\u0000\f\r\u0001\u0000\u0000\u0000\r\u000f"+
		"\u0001\u0000\u0000\u0000\u000e\b\u0001\u0000\u0000\u0000\u000f\u0012\u0001"+
		"\u0000\u0000\u0000\u0010\u000e\u0001\u0000\u0000\u0000\u0010\u0011\u0001"+
		"\u0000\u0000\u0000\u0011\u0013\u0001\u0000\u0000\u0000\u0012\u0010\u0001"+
		"\u0000\u0000\u0000\u0013\u0015\u0005\u0001\u0000\u0000\u0014\u0016\u0005"+
		"$\u0000\u0000\u0015\u0014\u0001\u0000\u0000\u0000\u0016\u0017\u0001\u0000"+
		"\u0000\u0000\u0017\u0015\u0001\u0000\u0000\u0000\u0017\u0018\u0001\u0000"+
		"\u0000\u0000\u0018\u0019\u0001\u0000\u0000\u0000\u0019\u001d\u0003\u0004"+
		"\u0002\u0000\u001a\u001c\u0005$\u0000\u0000\u001b\u001a\u0001\u0000\u0000"+
		"\u0000\u001c\u001f\u0001\u0000\u0000\u0000\u001d\u001b\u0001\u0000\u0000"+
		"\u0000\u001d\u001e\u0001\u0000\u0000\u0000\u001e \u0001\u0000\u0000\u0000"+
		"\u001f\u001d\u0001\u0000\u0000\u0000 0\u0005\u0002\u0000\u0000!#\u0005"+
		"$\u0000\u0000\"!\u0001\u0000\u0000\u0000#&\u0001\u0000\u0000\u0000$\""+
		"\u0001\u0000\u0000\u0000$%\u0001\u0000\u0000\u0000%\'\u0001\u0000\u0000"+
		"\u0000&$\u0001\u0000\u0000\u0000\'+\u0003\u0002\u0001\u0000(*\u0005$\u0000"+
		"\u0000)(\u0001\u0000\u0000\u0000*-\u0001\u0000\u0000\u0000+)\u0001\u0000"+
		"\u0000\u0000+,\u0001\u0000\u0000\u0000,/\u0001\u0000\u0000\u0000-+\u0001"+
		"\u0000\u0000\u0000.$\u0001\u0000\u0000\u0000/2\u0001\u0000\u0000\u0000"+
		"0.\u0001\u0000\u0000\u000001\u0001\u0000\u0000\u000013\u0001\u0000\u0000"+
		"\u000020\u0001\u0000\u0000\u000034\u0005\u0000\u0000\u00014\u0001\u0001"+
		"\u0000\u0000\u000056\u0005\u000f\u0000\u000068\u0005#\u0000\u000079\u0005"+
		"$\u0000\u000087\u0001\u0000\u0000\u00009:\u0001\u0000\u0000\u0000:8\u0001"+
		"\u0000\u0000\u0000:;\u0001\u0000\u0000\u0000;E\u0001\u0000\u0000\u0000"+
		"<=\u0005\u0011\u0000\u0000=?\u0005#\u0000\u0000>@\u0005$\u0000\u0000?"+
		">\u0001\u0000\u0000\u0000@A\u0001\u0000\u0000\u0000A?\u0001\u0000\u0000"+
		"\u0000AB\u0001\u0000\u0000\u0000BD\u0001\u0000\u0000\u0000C<\u0001\u0000"+
		"\u0000\u0000DG\u0001\u0000\u0000\u0000EC\u0001\u0000\u0000\u0000EF\u0001"+
		"\u0000\u0000\u0000FN\u0001\u0000\u0000\u0000GE\u0001\u0000\u0000\u0000"+
		"HJ\u0005\u0012\u0000\u0000IK\u0005$\u0000\u0000JI\u0001\u0000\u0000\u0000"+
		"KL\u0001\u0000\u0000\u0000LJ\u0001\u0000\u0000\u0000LM\u0001\u0000\u0000"+
		"\u0000MO\u0001\u0000\u0000\u0000NH\u0001\u0000\u0000\u0000NO\u0001\u0000"+
		"\u0000\u0000OP\u0001\u0000\u0000\u0000PX\u0003\u0004\u0002\u0000QR\u0005"+
		"\u0013\u0000\u0000RT\u0003\u0006\u0003\u0000SU\u0005$\u0000\u0000TS\u0001"+
		"\u0000\u0000\u0000UV\u0001\u0000\u0000\u0000VT\u0001\u0000\u0000\u0000"+
		"VW\u0001\u0000\u0000\u0000WY\u0001\u0000\u0000\u0000XQ\u0001\u0000\u0000"+
		"\u0000XY\u0001\u0000\u0000\u0000YZ\u0001\u0000\u0000\u0000Z[\u0005\u0010"+
		"\u0000\u0000[\u0003\u0001\u0000\u0000\u0000\\]\u0005\u0003\u0000\u0000"+
		"]_\u0003\u0006\u0003\u0000^`\u0005$\u0000\u0000_^\u0001\u0000\u0000\u0000"+
		"`a\u0001\u0000\u0000\u0000a_\u0001\u0000\u0000\u0000ab\u0001\u0000\u0000"+
		"\u0000bc\u0001\u0000\u0000\u0000cd\u0003\u0004\u0002\u0000d\u00f3\u0001"+
		"\u0000\u0000\u0000ef\u0005\u0004\u0000\u0000fh\u0005#\u0000\u0000gi\u0005"+
		"$\u0000\u0000hg\u0001\u0000\u0000\u0000ij\u0001\u0000\u0000\u0000jh\u0001"+
		"\u0000\u0000\u0000jk\u0001\u0000\u0000\u0000kl\u0001\u0000\u0000\u0000"+
		"lm\u0005\u0005\u0000\u0000mo\u0003\u0006\u0003\u0000np\u0005$\u0000\u0000"+
		"on\u0001\u0000\u0000\u0000pq\u0001\u0000\u0000\u0000qo\u0001\u0000\u0000"+
		"\u0000qr\u0001\u0000\u0000\u0000rs\u0001\u0000\u0000\u0000st\u0003\u0004"+
		"\u0002\u0000t\u00f3\u0001\u0000\u0000\u0000uv\u0005\u0006\u0000\u0000"+
		"vx\u0005#\u0000\u0000wy\u0005$\u0000\u0000xw\u0001\u0000\u0000\u0000y"+
		"z\u0001\u0000\u0000\u0000zx\u0001\u0000\u0000\u0000z{\u0001\u0000\u0000"+
		"\u0000{|\u0001\u0000\u0000\u0000|\u00f3\u0003\u0004\u0002\u0000}~\u0005"+
		"\u0007\u0000\u0000~\u0080\u0005#\u0000\u0000\u007f\u0081\u0005$\u0000"+
		"\u0000\u0080\u007f\u0001\u0000\u0000\u0000\u0081\u0082\u0001\u0000\u0000"+
		"\u0000\u0082\u0080\u0001\u0000\u0000\u0000\u0082\u0083\u0001\u0000\u0000"+
		"\u0000\u0083\u0084\u0001\u0000\u0000\u0000\u0084\u0085\u0005\b\u0000\u0000"+
		"\u0085\u0087\u0003\u0006\u0003\u0000\u0086\u0088\u0005$\u0000\u0000\u0087"+
		"\u0086\u0001\u0000\u0000\u0000\u0088\u0089\u0001\u0000\u0000\u0000\u0089"+
		"\u0087\u0001\u0000\u0000\u0000\u0089\u008a\u0001\u0000\u0000\u0000\u008a"+
		"\u0094\u0001\u0000\u0000\u0000\u008b\u008c\u0007\u0000\u0000\u0000\u008c"+
		"\u008e\u0003\u0006\u0003\u0000\u008d\u008f\u0005$\u0000\u0000\u008e\u008d"+
		"\u0001\u0000\u0000\u0000\u008f\u0090\u0001\u0000\u0000\u0000\u0090\u008e"+
		"\u0001\u0000\u0000\u0000\u0090\u0091\u0001\u0000\u0000\u0000\u0091\u0093"+
		"\u0001\u0000\u0000\u0000\u0092\u008b\u0001\u0000\u0000\u0000\u0093\u0096"+
		"\u0001\u0000\u0000\u0000\u0094\u0092\u0001\u0000\u0000\u0000\u0094\u0095"+
		"\u0001\u0000\u0000\u0000\u0095\u0097\u0001\u0000\u0000\u0000\u0096\u0094"+
		"\u0001\u0000\u0000\u0000\u0097\u0099\u0005\t\u0000\u0000\u0098\u009a\u0005"+
		"$\u0000\u0000\u0099\u0098\u0001\u0000\u0000\u0000\u009a\u009b\u0001\u0000"+
		"\u0000\u0000\u009b\u0099\u0001\u0000\u0000\u0000\u009b\u009c\u0001\u0000"+
		"\u0000\u0000\u009c\u009d\u0001\u0000\u0000\u0000\u009d\u009e\u0003\u0004"+
		"\u0002\u0000\u009e\u00f3\u0001\u0000\u0000\u0000\u009f\u00a0\u0005\n\u0000"+
		"\u0000\u00a0\u00a2\u0003\u0006\u0003\u0000\u00a1\u00a3\u0005$\u0000\u0000"+
		"\u00a2\u00a1\u0001\u0000\u0000\u0000\u00a3\u00a4\u0001\u0000\u0000\u0000"+
		"\u00a4\u00a2\u0001\u0000\u0000\u0000\u00a4\u00a5\u0001\u0000\u0000\u0000"+
		"\u00a5\u00a6\u0001\u0000\u0000\u0000\u00a6\u00aa\u0003\u0004\u0002\u0000"+
		"\u00a7\u00a9\u0005$\u0000\u0000\u00a8\u00a7\u0001\u0000\u0000\u0000\u00a9"+
		"\u00ac\u0001\u0000\u0000\u0000\u00aa\u00a8\u0001\u0000\u0000\u0000\u00aa"+
		"\u00ab\u0001\u0000\u0000\u0000\u00ab\u00ba\u0001\u0000\u0000\u0000\u00ac"+
		"\u00aa\u0001\u0000\u0000\u0000\u00ad\u00af\u0005\u000b\u0000\u0000\u00ae"+
		"\u00b0\u0005$\u0000\u0000\u00af\u00ae\u0001\u0000\u0000\u0000\u00b0\u00b1"+
		"\u0001\u0000\u0000\u0000\u00b1\u00af\u0001\u0000\u0000\u0000\u00b1\u00b2"+
		"\u0001\u0000\u0000\u0000\u00b2\u00b3\u0001\u0000\u0000\u0000\u00b3\u00b7"+
		"\u0003\u0004\u0002\u0000\u00b4\u00b6\u0005$\u0000\u0000\u00b5\u00b4\u0001"+
		"\u0000\u0000\u0000\u00b6\u00b9\u0001\u0000\u0000\u0000\u00b7\u00b5\u0001"+
		"\u0000\u0000\u0000\u00b7\u00b8\u0001\u0000\u0000\u0000\u00b8\u00bb\u0001"+
		"\u0000\u0000\u0000\u00b9\u00b7\u0001\u0000\u0000\u0000\u00ba\u00ad\u0001"+
		"\u0000\u0000\u0000\u00ba\u00bb\u0001\u0000\u0000\u0000\u00bb\u00bc\u0001"+
		"\u0000\u0000\u0000\u00bc\u00be\u0005\f\u0000\u0000\u00bd\u00bf\u0005$"+
		"\u0000\u0000\u00be\u00bd\u0001\u0000\u0000\u0000\u00bf\u00c0\u0001\u0000"+
		"\u0000\u0000\u00c0\u00be\u0001\u0000\u0000\u0000\u00c0\u00c1\u0001\u0000"+
		"\u0000\u0000\u00c1\u00c2\u0001\u0000\u0000\u0000\u00c2\u00c3\u0003\u0004"+
		"\u0002\u0000\u00c3\u00f3\u0001\u0000\u0000\u0000\u00c4\u00c5\u0005\r\u0000"+
		"\u0000\u00c5\u00c7\u0003\u0006\u0003\u0000\u00c6\u00c8\u0005$\u0000\u0000"+
		"\u00c7\u00c6\u0001\u0000\u0000\u0000\u00c8\u00c9\u0001\u0000\u0000\u0000"+
		"\u00c9\u00c7\u0001\u0000\u0000\u0000\u00c9\u00ca\u0001\u0000\u0000\u0000"+
		"\u00ca\u00cb\u0001\u0000\u0000\u0000\u00cb\u00cf\u0003\u0004\u0002\u0000"+
		"\u00cc\u00ce\u0005$\u0000\u0000\u00cd\u00cc\u0001\u0000\u0000\u0000\u00ce"+
		"\u00d1\u0001\u0000\u0000\u0000\u00cf\u00cd\u0001\u0000\u0000\u0000\u00cf"+
		"\u00d0\u0001\u0000\u0000\u0000\u00d0\u00d2\u0001\u0000\u0000\u0000\u00d1"+
		"\u00cf\u0001\u0000\u0000\u0000\u00d2\u00d4\u0005\u000e\u0000\u0000\u00d3"+
		"\u00d5\u0005$\u0000\u0000\u00d4\u00d3\u0001\u0000\u0000\u0000\u00d5\u00d6"+
		"\u0001\u0000\u0000\u0000\u00d6\u00d4\u0001\u0000\u0000\u0000\u00d6\u00d7"+
		"\u0001\u0000\u0000\u0000\u00d7\u00d8\u0001\u0000\u0000\u0000\u00d8\u00d9"+
		"\u0003\u0004\u0002\u0000\u00d9\u00f3\u0001\u0000\u0000\u0000\u00da\u00db"+
		"\u0005\u0015\u0000\u0000\u00db\u00dd\u0005#\u0000\u0000\u00dc\u00de\u0005"+
		"$\u0000\u0000\u00dd\u00dc\u0001\u0000\u0000\u0000\u00de\u00df\u0001\u0000"+
		"\u0000\u0000\u00df\u00dd\u0001\u0000\u0000\u0000\u00df\u00e0\u0001\u0000"+
		"\u0000\u0000\u00e0\u00e2\u0001\u0000\u0000\u0000\u00e1\u00da\u0001\u0000"+
		"\u0000\u0000\u00e1\u00e2\u0001\u0000\u0000\u0000\u00e2\u00e3\u0001\u0000"+
		"\u0000\u0000\u00e3\u00e4\u0005\u0014\u0000\u0000\u00e4\u00e8\u0005#\u0000"+
		"\u0000\u00e5\u00e7\u0003\u0006\u0003\u0000\u00e6\u00e5\u0001\u0000\u0000"+
		"\u0000\u00e7\u00ea\u0001\u0000\u0000\u0000\u00e8\u00e6\u0001\u0000\u0000"+
		"\u0000\u00e8\u00e9\u0001\u0000\u0000\u0000\u00e9\u00ec\u0001\u0000\u0000"+
		"\u0000\u00ea\u00e8\u0001\u0000\u0000\u0000\u00eb\u00ed\u0005$\u0000\u0000"+
		"\u00ec\u00eb\u0001\u0000\u0000\u0000\u00ed\u00ee\u0001\u0000\u0000\u0000"+
		"\u00ee\u00ec\u0001\u0000\u0000\u0000\u00ee\u00ef\u0001\u0000\u0000\u0000"+
		"\u00ef\u00f0\u0001\u0000\u0000\u0000\u00f0\u00f3\u0003\u0004\u0002\u0000"+
		"\u00f1\u00f3\u0001\u0000\u0000\u0000\u00f2\\\u0001\u0000\u0000\u0000\u00f2"+
		"e\u0001\u0000\u0000\u0000\u00f2u\u0001\u0000\u0000\u0000\u00f2}\u0001"+
		"\u0000\u0000\u0000\u00f2\u009f\u0001\u0000\u0000\u0000\u00f2\u00c4\u0001"+
		"\u0000\u0000\u0000\u00f2\u00e1\u0001\u0000\u0000\u0000\u00f2\u00f1\u0001"+
		"\u0000\u0000\u0000\u00f3\u0005\u0001\u0000\u0000\u0000\u00f4\u00fa\u0005"+
		"\u001f\u0000\u0000\u00f5\u00fa\u0005 \u0000\u0000\u00f6\u00fa\u0005!\u0000"+
		"\u0000\u00f7\u00fa\u0005\"\u0000\u0000\u00f8\u00fa\u0005#\u0000\u0000"+
		"\u00f9\u00f4\u0001\u0000\u0000\u0000\u00f9\u00f5\u0001\u0000\u0000\u0000"+
		"\u00f9\u00f6\u0001\u0000\u0000\u0000\u00f9\u00f7\u0001\u0000\u0000\u0000"+
		"\u00f9\u00f8\u0001\u0000\u0000\u0000\u00fa\u0007\u0001\u0000\u0000\u0000"+
		"&\f\u0010\u0017\u001d$+0:AELNVXajqz\u0082\u0089\u0090\u0094\u009b\u00a4"+
		"\u00aa\u00b1\u00b7\u00ba\u00c0\u00c9\u00cf\u00d6\u00df\u00e1\u00e8\u00ee"+
		"\u00f2\u00f9";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}