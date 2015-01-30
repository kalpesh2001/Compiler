/*
 *  The scanner definition for COOL.
 */
import java_cup.runtime.Symbol;


class CoolLexer implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 65536;
	private final int YY_EOF = 65537;

/*  Stuff enclosed in %{ %} is copied verbatim to the lexer class
 *  definition, all the extra variables/functions you want to use in the
 *  lexer actions should go here.  Don't remove or modify anything that
 *  was there initially.  */
    // Max size of string constants
    static int MAX_STR_CONST = 1025;
    int start_comment = 0; 
    // For assembling string constants
    StringBuffer string_buf = new StringBuffer();
    private int curr_lineno = 1;
    int get_curr_lineno() {
	return curr_lineno;
    }
    private AbstractSymbol filename;
    void set_filename(String fname) {
	filename = AbstractTable.stringtable.addString(fname);
    }
    AbstractSymbol curr_filename() {
	return filename;
    }
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	CoolLexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	CoolLexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private CoolLexer () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */
    // empty for now
	}

	private boolean yy_eof_done = false;
	private final int CLOSEC = 2;
	private final int STRING = 1;
	private final int YYINITIAL = 0;
	private final int SCOMMENT = 3;
	private final int yy_state_dtrans[] = {
		0,
		63,
		86,
		90
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NOT_ACCEPT,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NOT_ACCEPT,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NOT_ACCEPT,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NO_ANCHOR,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NO_ANCHOR,
		/* 127 */ YY_NO_ANCHOR,
		/* 128 */ YY_NO_ANCHOR,
		/* 129 */ YY_NO_ANCHOR,
		/* 130 */ YY_NO_ANCHOR,
		/* 131 */ YY_NO_ANCHOR,
		/* 132 */ YY_NO_ANCHOR,
		/* 133 */ YY_NO_ANCHOR,
		/* 134 */ YY_NO_ANCHOR,
		/* 135 */ YY_NO_ANCHOR,
		/* 136 */ YY_NO_ANCHOR,
		/* 137 */ YY_NO_ANCHOR,
		/* 138 */ YY_NO_ANCHOR,
		/* 139 */ YY_NO_ANCHOR,
		/* 140 */ YY_NO_ANCHOR,
		/* 141 */ YY_NO_ANCHOR,
		/* 142 */ YY_NO_ANCHOR,
		/* 143 */ YY_NO_ANCHOR,
		/* 144 */ YY_NO_ANCHOR,
		/* 145 */ YY_NO_ANCHOR,
		/* 146 */ YY_NO_ANCHOR,
		/* 147 */ YY_NO_ANCHOR,
		/* 148 */ YY_NO_ANCHOR,
		/* 149 */ YY_NO_ANCHOR,
		/* 150 */ YY_NO_ANCHOR,
		/* 151 */ YY_NO_ANCHOR,
		/* 152 */ YY_NO_ANCHOR,
		/* 153 */ YY_NO_ANCHOR,
		/* 154 */ YY_NO_ANCHOR,
		/* 155 */ YY_NO_ANCHOR,
		/* 156 */ YY_NO_ANCHOR,
		/* 157 */ YY_NO_ANCHOR,
		/* 158 */ YY_NO_ANCHOR,
		/* 159 */ YY_NO_ANCHOR,
		/* 160 */ YY_NO_ANCHOR,
		/* 161 */ YY_NO_ANCHOR,
		/* 162 */ YY_NO_ANCHOR,
		/* 163 */ YY_NO_ANCHOR,
		/* 164 */ YY_NO_ANCHOR,
		/* 165 */ YY_NO_ANCHOR,
		/* 166 */ YY_NO_ANCHOR,
		/* 167 */ YY_NO_ANCHOR,
		/* 168 */ YY_NO_ANCHOR,
		/* 169 */ YY_NO_ANCHOR,
		/* 170 */ YY_NO_ANCHOR,
		/* 171 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,65538,
"61,66:7,39,65,3,66,40,62,66:18,63,66,60,66:5,11,5,4,18,16,1,14,17,41:10,8,1" +
"5,9,6,7,66,10,42,43,44,45,46,26,43,47,48,43:2,49,43,50,51,52,43,53,54,31,55" +
",56,57,43:3,66,2,66:2,58,66,22,59,20,34,24,25,64,29,27,64:2,21,64,28,33,35," +
"64,30,23,37,38,32,36,64:3,13,66,12,19,66:65409,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,172,
"0,1,2,1:2,3,1,4,1,5,1,6,1:8,7,8,1,9,1,10,1:5,11:2,12,11:8,13,11:7,14,1:4,14" +
",1:7,15,16,17,18,1,13:2,19,13:8,11,13:5,20,21,22,23,24,25,26,27,28,29,30,31" +
",32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56" +
",57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81" +
",82,83,84,85,86,87,88,89,90,91,92,93,94,95,11,13,96,97,98,99,100,101,102,10" +
"3,104")[0];

	private int yy_nxt[][] = unpackFromString(105,67,
"1,2,3,4,5,6,7,3,8,9,10,11,12,13,14,15,16,17,18,19,20,121,161:2,163,65,21,87" +
",123,161:2,66,161,91,161,165,167,169,161,22,4,23,162:2,164,162,166,162,88,1" +
"22,124,92,168,162:4,170,3,161,24,3:2,22,161,22,3,-1:68,25,-1:70,26,-1:68,27" +
",-1:60,28,-1:4,29,-1:64,30,-1:82,161,171,125,161:16,-1:2,161,125,161:6,171," +
"161:10,-1:4,161,-1:22,162:7,68,162:11,-1:2,162:7,68,162:11,-1:4,162,-1:43,2" +
"3,-1:26,25:2,67,25:63,-1:20,161:19,-1:2,161:19,-1:4,161,-1:22,161:9,151,161" +
":9,-1:2,161:6,151,161:12,-1:4,161,-1:22,162:19,-1:2,162:19,-1:4,162,-1:3,50" +
",-1:2,50:35,-1:2,50:19,-1,50:4,-1,50,1,50,64,51,50:35,52,53,50:19,54,55,50:" +
"3,56,50,-1,57:24,53,57:2,58,57:8,56,57:21,52,57:7,-1:20,161:2,133,161:4,31," +
"161:11,-1:2,161,133,161:5,31,161:11,-1:4,161,-1:22,162:9,126,162:9,-1:2,162" +
":6,126,162:12,-1:4,162,-1:22,162:9,148,162:9,-1:2,162:6,148,162:12,-1:4,162" +
",-1:7,61,-1:61,1,3:66,-1:20,161:3,135,161,32:2,161,33,161:10,-1:2,161:9,33," +
"161:3,135,161:5,-1:4,161,-1:22,162:3,136,162,69:2,162,70,162:10,-1:2,162:9," +
"70,162:3,136,162:5,-1:4,162,-1:6,62,-1:62,1,59:2,60,85,59:6,89,59:50,60,59:" +
"4,-1:20,161:5,34:2,161:12,-1:2,161:19,-1:4,161,-1:22,162:5,71:2,162:12,-1:2" +
",162:19,-1:4,162,-1:22,161:11,35,161:5,35,161,-1:2,161:19,-1:4,161,-1:22,16" +
"2:11,72,162:5,72,162,-1:2,162:19,-1:4,162,-1:22,161:16,36,161:2,-1:2,161:16" +
",36,161:2,-1:4,161,-1:22,162:16,73,162:2,-1:2,162:16,73,162:2,-1:4,162,-1:2" +
"2,161:11,37,161:5,37,161,-1:2,161:19,-1:4,161,-1:22,162:11,74,162:5,74,162," +
"-1:2,162:19,-1:4,162,-1:22,161:4,38,161:14,-1:2,161:5,38,161:13,-1:4,161,-1" +
":22,162:8,42,162:10,-1:2,162:9,42,162:9,-1:4,162,-1:22,161:15,39,161:3,-1:2" +
",161:11,39,161:7,-1:4,161,-1:22,162:4,75,162:14,-1:2,162:5,75,162:13,-1:4,1" +
"62,-1:22,161:4,40,161:14,-1:2,161:5,40,161:13,-1:4,161,-1:22,162:4,77,162:1" +
"4,-1:2,162:5,77,162:13,-1:4,162,-1:22,41,161:18,-1:2,161:3,41,161:15,-1:4,1" +
"61,-1:22,78,162:18,-1:2,162:3,78,162:15,-1:4,162,-1:22,161,43,161:17,-1:2,1" +
"61:8,43,161:10,-1:4,161,-1:22,162:15,76,162:3,-1:2,162:11,76,162:7,-1:4,162" +
",-1:22,161:8,79,161:10,-1:2,161:9,79,161:9,-1:4,161,-1:22,162,80,162:17,-1:" +
"2,162:8,80,162:10,-1:4,162,-1:22,161:4,44,161:14,-1:2,161:5,44,161:13,-1:4," +
"161,-1:22,162:3,81,162:15,-1:2,162:13,81,162:5,-1:4,162,-1:22,161:3,45,161:" +
"15,-1:2,161:13,45,161:5,-1:4,161,-1:22,162:4,82,162:14,-1:2,162:5,82,162:13" +
",-1:4,162,-1:22,161:4,46,161:14,-1:2,161:5,46,161:13,-1:4,161,-1:22,162:14," +
"83,162:4,-1:2,162:4,83,162:14,-1:4,162,-1:22,161:4,47,161:14,-1:2,161:5,47," +
"161:13,-1:4,161,-1:22,162:3,84,162:15,-1:2,162:13,84,162:5,-1:4,162,-1:22,1" +
"61:14,48,161:4,-1:2,161:4,48,161:14,-1:4,161,-1:22,161:3,49,161:15,-1:2,161" +
":13,49,161:5,-1:4,161,-1:22,161:4,93,161:8,127,161:5,-1:2,161:5,93,161:4,12" +
"7,161:8,-1:4,161,-1:22,162:4,94,162:8,138,162:5,-1:2,162:5,94,162:4,138,162" +
":8,-1:4,162,-1:22,161:4,95,161:8,97,161:5,-1:2,161:5,95,161:4,97,161:8,-1:4" +
",161,-1:22,162:4,96,162:8,98,162:5,-1:2,162:5,96,162:4,98,162:8,-1:4,162,-1" +
":22,161:3,99,161:15,-1:2,161:13,99,161:5,-1:4,161,-1:22,162:4,100,162:14,-1" +
":2,162:5,100,162:13,-1:4,162,-1:22,161:13,101,161:5,-1:2,161:10,101,161:8,-" +
"1:4,161,-1:22,162:2,144,162:16,-1:2,162,144,162:17,-1:4,162,-1:22,161:3,103" +
",161:15,-1:2,161:13,103,161:5,-1:4,161,-1:22,162:3,102,162:15,-1:2,162:13,1" +
"02,162:5,-1:4,162,-1:22,161:2,105,161:16,-1:2,161,105,161:17,-1:4,161,-1:22" +
",162:3,104,162:15,-1:2,162:13,104,162:5,-1:4,162,-1:22,161,147,161:17,-1:2," +
"161:8,147,161:10,-1:4,161,-1:22,162:2,106,162:16,-1:2,162,106,162:17,-1:4,1" +
"62,-1:22,161:12,149,161:6,-1:2,161:15,149,161:3,-1:4,161,-1:22,162:12,146,1" +
"62:6,-1:2,162:15,146,162:3,-1:4,162,-1:22,161:13,107,161:5,-1:2,161:10,107," +
"161:8,-1:4,161,-1:22,162:13,108,162:5,-1:2,162:10,108,162:8,-1:4,162,-1:22," +
"161:7,153,161:11,-1:2,161:7,153,161:11,-1:4,161,-1:22,162:13,110,162:5,-1:2" +
",162:10,110,162:8,-1:4,162,-1:22,161:4,109,161:14,-1:2,161:5,109,161:13,-1:" +
"4,161,-1:22,162:7,150,162:11,-1:2,162:7,150,162:11,-1:4,162,-1:22,161:18,11" +
"1,-1:2,161:14,111,161:4,-1:4,161,-1:22,162:3,112,162:15,-1:2,162:13,112,162" +
":5,-1:4,162,-1:22,161:3,113,161:15,-1:2,161:13,113,161:5,-1:4,161,-1:22,162" +
":13,152,162:5,-1:2,162:10,152,162:8,-1:4,162,-1:22,161:3,115,161:15,-1:2,16" +
"1:13,115,161:5,-1:4,161,-1:22,162:4,154,162:14,-1:2,162:5,154,162:13,-1:4,1" +
"62,-1:22,161:13,155,161:5,-1:2,161:10,155,161:8,-1:4,161,-1:22,162,114,162:" +
"17,-1:2,162:8,114,162:10,-1:4,162,-1:22,161:4,157,161:14,-1:2,161:5,157,161" +
":13,-1:4,161,-1:22,162:7,116,162:11,-1:2,162:7,116,162:11,-1:4,162,-1:22,16" +
"1,117,161:17,-1:2,161:8,117,161:10,-1:4,161,-1:22,162:10,156,162:8,-1:2,162" +
":12,156,162:6,-1:4,162,-1:22,161:7,119,161:11,-1:2,161:7,119,161:11,-1:4,16" +
"1,-1:22,162:7,158,162:11,-1:2,162:7,158,162:11,-1:4,162,-1:22,161:10,159,16" +
"1:8,-1:2,161:12,159,161:6,-1:4,161,-1:22,162:11,118,162:5,118,162,-1:2,162:" +
"19,-1:4,162,-1:22,161:7,160,161:11,-1:2,161:7,160,161:11,-1:4,161,-1:22,161" +
":11,120,161:5,120,161,-1:2,161:19,-1:4,161,-1:22,161,129,161,131,161:15,-1:" +
"2,161:8,129,161:4,131,161:5,-1:4,161,-1:22,162,128,130,162:16,-1:2,162,130," +
"162:6,128,162:10,-1:4,162,-1:22,161:13,137,161:5,-1:2,161:10,137,161:8,-1:4" +
",161,-1:22,162,132,162,134,162:15,-1:2,162:8,132,162:4,134,162:5,-1:4,162,-" +
"1:22,161:9,139,161:9,-1:2,161:6,139,161:12,-1:4,161,-1:22,162:13,140,162:5," +
"-1:2,162:10,140,162:8,-1:4,162,-1:22,161:9,141,143,161:8,-1:2,161:6,141,161" +
":5,143,161:6,-1:4,161,-1:22,162:9,142,162:9,-1:2,162:6,142,162:12,-1:4,162," +
"-1:22,161:2,145,161:16,-1:2,161,145,161:17,-1:4,161,-1:2");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

/*  Stuff enclosed in %eofval{ %eofval} specifies java code that is
 *  executed when end-of-file is reached.  If you use multiple lexical
 *  states and want to do something special if an EOF is encountered in
 *  one of those states, place your code in the switch statement.
 *  Ultimately, you should return the EOF symbol, or your lexer won't
 *  work.  */
    switch(yy_lexical_state) {
    case YYINITIAL:
	/* nothing special to do in the initial state */
	break;
	/* If necessary, add code for other states here, e.g: */
	   case CLOSEC:
	   return new Symbol(TokenConstants.ERROR,"EOF in comment"); 
	   case SCOMMENT:
	   return new Symbol(TokenConstants.ERROR,"EOF in comment"); 
    }
    return new Symbol(TokenConstants.EOF);
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{return new Symbol(TokenConstants.MINUS); }
					case -3:
						break;
					case 3:
						{ /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
                                  /*System.err.println("LEXER BUG - UNMATCHED at line: " + yytext()+yyline); */
				  return new Symbol(TokenConstants.ERROR,yytext());}
					case -4:
						break;
					case 4:
						{curr_lineno++;}
					case -5:
						break;
					case 5:
						{return new Symbol(TokenConstants.MULT); }
					case -6:
						break;
					case 6:
						{return new Symbol(TokenConstants.RPAREN); }
					case -7:
						break;
					case 7:
						{return new Symbol(TokenConstants.EQ); }
					case -8:
						break;
					case 8:
						{ return new Symbol(TokenConstants.COLON);}
					case -9:
						break;
					case 9:
						{return new Symbol(TokenConstants.LT); }
					case -10:
						break;
					case 10:
						{
                                  return new Symbol(TokenConstants.AT); }
					case -11:
						break;
					case 11:
						{return new Symbol(TokenConstants.LPAREN); }
					case -12:
						break;
					case 12:
						{return new Symbol(TokenConstants.RBRACE); }
					case -13:
						break;
					case 13:
						{return new Symbol(TokenConstants.LBRACE); }
					case -14:
						break;
					case 14:
						{return new Symbol(TokenConstants.DOT); }
					case -15:
						break;
					case 15:
						{return new Symbol(TokenConstants.SEMI); }
					case -16:
						break;
					case 16:
						{return new Symbol(TokenConstants.COMMA); }
					case -17:
						break;
					case 17:
						{return new Symbol(TokenConstants.DIV); }
					case -18:
						break;
					case 18:
						{return new Symbol(TokenConstants.PLUS); }
					case -19:
						break;
					case 19:
						{return new Symbol(TokenConstants.NEG); }
					case -20:
						break;
					case 20:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -21:
						break;
					case 21:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -22:
						break;
					case 22:
						{}
					case -23:
						break;
					case 23:
						{
						return new Symbol(TokenConstants.INT_CONST,AbstractTable.inttable.addString(yytext()));}
					case -24:
						break;
					case 24:
						{string_buf.setLength(0);yybegin(STRING);}
					case -25:
						break;
					case 25:
						{/*System.out.println("Matched comment"+ yytext());*/}
					case -26:
						break;
					case 26:
						{return new Symbol(TokenConstants.ERROR,"Unmatched *)");}
					case -27:
						break;
					case 27:
						{
                                  return new Symbol(TokenConstants.DARROW); }
					case -28:
						break;
					case 28:
						{return new Symbol(TokenConstants.ASSIGN);}
					case -29:
						break;
					case 29:
						{return new Symbol(TokenConstants.LE); }
					case -30:
						break;
					case 30:
						{/*System.out.println("Starting comment parsing");*/ yybegin(SCOMMENT);start_comment = 1;}
					case -31:
						break;
					case 31:
						{return new Symbol(TokenConstants.FI);}
					case -32:
						break;
					case 32:
						{return new Symbol(TokenConstants.IF);}
					case -33:
						break;
					case 33:
						{return new Symbol(TokenConstants.IN);}
					case -34:
						break;
					case 34:
						{return new Symbol(TokenConstants.OF);}
					case -35:
						break;
					case 35:
						{return new Symbol(TokenConstants.LET);}
					case -36:
						break;
					case 36:
						{return new Symbol(TokenConstants.NEW);}
					case -37:
						break;
					case 37:
						{return new Symbol(TokenConstants.NOT);}
					case -38:
						break;
					case 38:
						{return new Symbol(TokenConstants.CASE);}
					case -39:
						break;
					case 39:
						{return new Symbol(TokenConstants.LOOP);}
					case -40:
						break;
					case 40:
						{return new Symbol(TokenConstants.ELSE);}
					case -41:
						break;
					case 41:
						{return new Symbol(TokenConstants.ESAC);}
					case -42:
						break;
					case 42:
						{return new Symbol(TokenConstants.THEN);}
					case -43:
						break;
					case 43:
						{return new Symbol(TokenConstants.POOL);}
					case -44:
						break;
					case 44:
						{return new Symbol(TokenConstants.BOOL_CONST,new Boolean(true));}
					case -45:
						break;
					case 45:
						{return new Symbol(TokenConstants.CLASS); }
					case -46:
						break;
					case 46:
						{return new Symbol(TokenConstants.BOOL_CONST,new Boolean(false));}
					case -47:
						break;
					case 47:
						{return new Symbol(TokenConstants.WHILE);}
					case -48:
						break;
					case 48:
						{return new Symbol(TokenConstants.ISVOID);}
					case -49:
						break;
					case 49:
						{return new Symbol(TokenConstants.INHERITS);}
					case -50:
						break;
					case 50:
						{string_buf.append(yytext());/*System.out.println("String appended:"+string_buf);*/}
					case -51:
						break;
					case 51:
						{yybegin(YYINITIAL);curr_lineno++;return new Symbol(TokenConstants.ERROR,"Unterminated string constant");}
					case -52:
						break;
					case 52:
						{string_buf.append('\b');}
					case -53:
						break;
					case 53:
						{string_buf.append('\f');}
					case -54:
						break;
					case 54:
						{yybegin(YYINITIAL); 
					if (string_buf.length() > MAX_STR_CONST)
						return new Symbol(TokenConstants.ERROR,"String constant too long");
					else 
						return new Symbol(TokenConstants.STR_CONST,AbstractTable.stringtable.addString(string_buf.toString()));
					}
					case -55:
						break;
					case 55:
						{yybegin(YYINITIAL);return new Symbol(TokenConstants.ERROR,"String contains null character");}
					case -56:
						break;
					case 56:
						{string_buf.append('\t');}
					case -57:
						break;
					case 57:
						{string_buf.append(yytext().charAt(1));}
					case -58:
						break;
					case 58:
						{string_buf.append('\n');curr_lineno++;/*System.out.println("Append newline:"+string_buf);*/}
					case -59:
						break;
					case 59:
						{}
					case -60:
						break;
					case 60:
						{curr_lineno++;}
					case -61:
						break;
					case 61:
						{/*System.out.println("close comment:"+yytext()); */
				  	start_comment--;
				  	if (start_comment == 0) yybegin(YYINITIAL);}
					case -62:
						break;
					case 62:
						{/*System.out.println("Add one for opening comment");*/ start_comment++;}
					case -63:
						break;
					case 64:
						{ /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
                                  /*System.err.println("LEXER BUG - UNMATCHED at line: " + yytext()+yyline); */
				  return new Symbol(TokenConstants.ERROR,yytext());}
					case -64:
						break;
					case 65:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -65:
						break;
					case 66:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -66:
						break;
					case 67:
						{/*System.out.println("Matched comment"+ yytext());*/}
					case -67:
						break;
					case 68:
						{return new Symbol(TokenConstants.FI);}
					case -68:
						break;
					case 69:
						{return new Symbol(TokenConstants.IF);}
					case -69:
						break;
					case 70:
						{return new Symbol(TokenConstants.IN);}
					case -70:
						break;
					case 71:
						{return new Symbol(TokenConstants.OF);}
					case -71:
						break;
					case 72:
						{return new Symbol(TokenConstants.LET);}
					case -72:
						break;
					case 73:
						{return new Symbol(TokenConstants.NEW);}
					case -73:
						break;
					case 74:
						{return new Symbol(TokenConstants.NOT);}
					case -74:
						break;
					case 75:
						{return new Symbol(TokenConstants.CASE);}
					case -75:
						break;
					case 76:
						{return new Symbol(TokenConstants.LOOP);}
					case -76:
						break;
					case 77:
						{return new Symbol(TokenConstants.ELSE);}
					case -77:
						break;
					case 78:
						{return new Symbol(TokenConstants.ESAC);}
					case -78:
						break;
					case 79:
						{return new Symbol(TokenConstants.THEN);}
					case -79:
						break;
					case 80:
						{return new Symbol(TokenConstants.POOL);}
					case -80:
						break;
					case 81:
						{return new Symbol(TokenConstants.CLASS); }
					case -81:
						break;
					case 82:
						{return new Symbol(TokenConstants.WHILE);}
					case -82:
						break;
					case 83:
						{return new Symbol(TokenConstants.ISVOID);}
					case -83:
						break;
					case 84:
						{return new Symbol(TokenConstants.INHERITS);}
					case -84:
						break;
					case 85:
						{}
					case -85:
						break;
					case 87:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -86:
						break;
					case 88:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -87:
						break;
					case 89:
						{}
					case -88:
						break;
					case 91:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -89:
						break;
					case 92:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -90:
						break;
					case 93:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -91:
						break;
					case 94:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -92:
						break;
					case 95:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -93:
						break;
					case 96:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -94:
						break;
					case 97:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -95:
						break;
					case 98:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -96:
						break;
					case 99:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -97:
						break;
					case 100:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -98:
						break;
					case 101:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -99:
						break;
					case 102:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -100:
						break;
					case 103:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -101:
						break;
					case 104:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -102:
						break;
					case 105:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -103:
						break;
					case 106:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -104:
						break;
					case 107:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -105:
						break;
					case 108:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -106:
						break;
					case 109:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -107:
						break;
					case 110:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -108:
						break;
					case 111:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -109:
						break;
					case 112:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -110:
						break;
					case 113:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -111:
						break;
					case 114:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -112:
						break;
					case 115:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -113:
						break;
					case 116:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -114:
						break;
					case 117:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -115:
						break;
					case 118:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -116:
						break;
					case 119:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -117:
						break;
					case 120:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -118:
						break;
					case 121:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -119:
						break;
					case 122:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -120:
						break;
					case 123:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -121:
						break;
					case 124:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -122:
						break;
					case 125:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -123:
						break;
					case 126:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -124:
						break;
					case 127:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -125:
						break;
					case 128:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -126:
						break;
					case 129:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -127:
						break;
					case 130:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -128:
						break;
					case 131:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -129:
						break;
					case 132:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -130:
						break;
					case 133:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -131:
						break;
					case 134:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -132:
						break;
					case 135:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -133:
						break;
					case 136:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -134:
						break;
					case 137:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -135:
						break;
					case 138:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -136:
						break;
					case 139:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -137:
						break;
					case 140:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -138:
						break;
					case 141:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -139:
						break;
					case 142:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -140:
						break;
					case 143:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -141:
						break;
					case 144:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -142:
						break;
					case 145:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -143:
						break;
					case 146:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -144:
						break;
					case 147:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -145:
						break;
					case 148:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -146:
						break;
					case 149:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -147:
						break;
					case 150:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -148:
						break;
					case 151:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -149:
						break;
					case 152:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -150:
						break;
					case 153:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -151:
						break;
					case 154:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -152:
						break;
					case 155:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -153:
						break;
					case 156:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -154:
						break;
					case 157:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -155:
						break;
					case 158:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -156:
						break;
					case 159:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -157:
						break;
					case 160:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -158:
						break;
					case 161:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -159:
						break;
					case 162:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -160:
						break;
					case 163:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -161:
						break;
					case 164:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -162:
						break;
					case 165:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -163:
						break;
					case 166:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -164:
						break;
					case 167:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -165:
						break;
					case 168:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -166:
						break;
					case 169:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -167:
						break;
					case 170:
						{
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
					case -168:
						break;
					case 171:
						{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
					case -169:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
