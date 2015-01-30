/*
Copyright (c) 2000 The Regents of the University of California.
All rights reserved.

Permission to use, copy, modify, and distribute this software for any
purpose, without fee, and without written agreement is hereby granted,
provided that the above copyright notice and the following two
paragraphs appear in all copies of this software.

IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR
DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES ARISING OUT
OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF THE UNIVERSITY OF
CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
AND FITNESS FOR A PARTICULAR PURPOSE.  THE SOFTWARE PROVIDED HEREUNDER IS
ON AN "AS IS" BASIS, AND THE UNIVERSITY OF CALIFORNIA HAS NO OBLIGATION TO
PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
*/

/** An example of using a symbol table. */
class SymtabExample_test {
    public static void main(String args[]) {
	// crate a new symbol table; entries will be name/age pairs
	SymbolTable map = new SymbolTable();

	// create some names
	AbstractSymbol fred = AbstractTable.stringtable.addString("Fred");
	AbstractSymbol mary = AbstractTable.stringtable.addString("Mary");
	AbstractSymbol miguel = AbstractTable.stringtable.addString("Miguel");
	
	map.enterScope();

	// add a couple of entries mapping name to age.
	// note the second argument must be a pointer to an integer
	// add a scope, add more names:
    }	
}

 
