# CNFParseTree
This is a Git repository that contains the Java program for the main Assignment of _Foundations of Computation_ module
of the MSc Computer Science of University of Bath

A parser is a key component of most compilers; its purpose is to analyze a string of symbols and perform some operations which include:

-checking that the string of symbols forms a syntactically correct expression given some underlining formal grammar;

-when this is the case, transform that into a suitable data structure.

The syntax of programming languages is often described by means of context-free grammars. Therefore, given a context-free grammar G, the two steps above may be understood as deciding whether a string w belongs to the language L(G), and then,  when this is the case, construct a parse tree for w.

**Grammar G:**

The following grammar G generates the language of syntactically correct arithmetic expressions involving addition, multiplication and a single variable name. Being able to parse text is the first step of being able to compile code, and this grammar represents a fragment of the kind of expressions you might find in many common programming languages. This grammar is unambiguous. 
<pre>
Let G=(V,Σ,R,E), where E is the start symbol, and the variables V, terminals Σ, and rules R, are as follows:

V={T,F,E,C} 
Σ={+,∗,−,1,0,x}
R={E→E+T,
   E→T,
   T→T∗F,
   T→F,
   F→C,
   F→−C,
   C→1,
   C→0,
   C→x} 

The symbols E, T, F, and C are abbreviations for expression, term, factor, and constant respectively.
</pre>

**Algorithm:**
We know that if G is in Chomsky normal form, then any derivation of a string w∈L(G) will have exactly 2n−1 steps where n=|w|. This is the basis for this algorithm.

On an input w for grammar G:

-List all derivations with 2n−1 steps, where n=|w|, unless n=0, then instead list all derivations with 1 step.

-If any of these derivations generate w, then accept. Otherwise, reject.

Every string which is generated by the grammar G will have (at least one) parse tree in that grammar.
