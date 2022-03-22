//------------------------------------------------------------------------------------------------------------------------------//
//---------------------------------------Parser-Coursework-Algorighm-1-BF-------------------------------------------------------//
//------------------------------------------------------------------------------------------------------------------------------//
                                                                  //------------------------------------------------------------//
                                                                  //-------------------COMMENTS COLUMN--------------------------//
                                                                  //------------------------------------------------------------//
import computation.contextfreegrammar.*;                          //
import computation.parser.*;                                      //
import computation.parsetree.*;                                   // Importing packages.
import computation.derivation.*;                                  //
import java.util.*;                                               //
                                                                  //
public class Parser implements IParser {                          //
  private ArrayList<Derivation> classDerivations = new ArrayList<>(); // Creating a new Array List for all out derivations.
  private final Word emptyWord = new Word();                      // Final variable (cannot be changed) to store the empty string.
  private boolean finalResult = false;                            // Setting a local bolean variable for the final outcome task.
                                                                  // Initialising finalResult variable as false.
                                                                  //
//---------------------------------------Task-1-Defining-If-String-Is-In-Language ----------------------------------------------//
                                                                  //------------------------------------------------------------//
                                                                  //-------------------COMMENTS COLUMN--------------------------//
                                                                  //------------------------------------------------------------//
  private void mainLogic(ContextFreeGrammar cfg, Word w) {        // 
                                                                  //
    int n = w.length();                                           // Defining length of string w.
    int steps = 2*n-1;                                            // Defining total steps of derivations needed from CNF attribute.
                                                                  //
    List<Rule> rules = cfg.getRules();                            // Storing cfg rules in a List called rules.
    Word startVar = new Word(cfg.getStartVariable());             // Getting initial variable (S) from cfg.
    Word prevDeriv;                                               // Storing string from a previous derivation.
                                                                  //
    ArrayList<Derivation> derivs = new ArrayList<>();             // Creating a new ArrayList store all deivations for string.
                                                                  // Making use of Derivations.java class.
                                                                  //
    ArrayList<Derivation> finalDerivs = new ArrayList<>();        // Creating a new ArrayList store only final derivations.
    ArrayList<Derivation> perfectDerivs = new ArrayList<>();      // Creating a new ArrayList store only perfect derivations.
    derivs.add(new Derivation(startVar));                         // Creating the -1 step for starVar.
                                                                  // Considering the comments isInLanguage in Derivation.java class.             
    Word ruleLeft;                                                // Storing left hand side (variable) of rule.
    Word ruleRight;                                               // Storing right hand side (expansion) of rule.
    Word symbolToWord;                                            // Coverting symbols to strings.
    Word wordAfterStep;                                           // Storing the output of replace().
                                                                  //
    boolean isInLanguage = false;                                 // Setting a local bolean variable for the outcome of the "is in language?" task.
                                                                  // Initialising isInLanguage variable as False.
    if (n == 0){                                                  // If statement for the case that n = 0. Listing derivations with 1 step as stated in the data sheet.
      for (Rule rule:rules){                                      // Running through all rules stored in the "rules" list created above.
        if ( new Word(rule.getVariable()).equals(startVar)        // Checking if the string equals the start variable,
            && rule.getExpansion().equals(emptyWord)){            // AND if it is equal to an empty string.
          finalResult = true;                                     // Changing finalResult variable to True if the above conditions are satisfied.
          return;                                                 // Exiting method at this point. Executing the rest of the code is unecessary.            
        }                                                         //
      }                                                           //
      finalResult = false;                                        // Keepeing finalResult variable as False, as code did not ent
      return;                                                     // Exiting method at this point. Executing the rest of the code is unecessary.
    }                                                             //
                                                                  //
    int tracer = 0;                                               // Initialising a tracer for the position replace().
                                                                  //
    for (int i = 1; i <= steps; i++) {                            // Running through all steps one by one (i=1).
                                                                  //
      for (Derivation j:derivs) {                                 // Running through all derivations in derivs list, created above, for the current step i.
        prevDeriv = j.getLatestWord();                            // Changing the word from previous derivation with the latest word from the derivation j.
        int prevDerivLength = prevDeriv.length();                 // Storing the length of the string from deriation j to a new variable called "prevDerivLength".
        if (isTerms(cfg, prevDeriv)) {                            // Using method "isTerms" (see Appendix) to check if the current string only consists of terminals.
           continue;                                              // Exiting the loop, as there are no more derivations for the current string.
        }                                                         // Otherwise,
        for (tracer = 0; tracer < prevDerivLength; tracer++) {    // running a new loop and searching for the first variable.
          symbolToWord = new Word(prevDeriv.get(tracer));         //
          if (!isTerms(cfg, symbolToWord)) {                      // Checking if "symbolToWord" from previous line consists only of terminals,
            break;                                                // breaking if it is False.
          }                                                       //
        }                                                         //
                                                                  //
        symbolToWord = new Word(prevDeriv.get(tracer));           // Convert symbol into a string (using the tracer!).
        for (Rule rule:rules) {                                   // Running through all the rules stored in Rule List.
          ruleLeft = new Word(rule.getVariable());                // Storing in the ruleLeft variable, the variable of the rule (for this iteration).
          if (ruleLeft.equals(symbolToWord)) {                    // If ruleLeft matches the symbol/character of this derivaton stored in symbolToWord,
            ruleRight = rule.getExpansion();                      // we store the expansion of that rule to the ruleRight variable.
            if (ruleRight.equals(emptyWord)) {                    // Checking if the above expansion leads to the empty string/
              continue;                                           // Exiting the loop, as there are no more derivations for the current string.
            }                                                     // Otherwise,
            wordAfterStep = prevDeriv.replace(tracer, ruleRight); // storing into the wordAfterStep the new string: previous string,
                                                                  // but we replacing the character at postion "tracer" with the string stored into ruleRight.
            Derivation deriv = new Derivation(j);                 // Adding (tracking) a new derivation into the the Derivation Array List.
            deriv.addStep(wordAfterStep, rule, tracer);           // Using addStep method found in Derivation.java to store the string, the rule, and the index/tracer.
            perfectDerivs.add(deriv);                             //
            if (wordAfterStep.equals(w) && i == steps) {          // Checking if the word after the derivation matches the given string, AND if i equals the total number of steps (2n-1).
              finalDerivs.add(deriv);                             // If True, we add it to the finalDerivs List. No more derivations are possible,
              isInLanguage = true;                                // the given string is in the language (changing isInLanguage to True).
            }                                                     //
          }                                                       //
        }                                                         //
      }                                                           //
      derivs = new ArrayList<>(perfectDerivs);                    // Storing the perfectDerivs List to derivs List and,
      perfectDerivs.clear();                                      // clearing the perfectDerivs List.
    }                                                             //
                                                                  // 
    classDerivations = finalDerivs;                               // Storing the finalDerivs into a new Array List. Needed for Task 2 - Generating Parse Tree.
                                                                  //
    finalResult = isInLanguage;                                   // Storing the the outcome of the above process into the finalResuslt variable (False/True).                        
  }                                                               // It will also be used for Task 2 - Generating Parse Tree.
//---------------------------------------End-Task-1-Defining-If-String-Is-In-Language ------------------------------------------//

//-------------------------------------------------------Task-2-Generating-Parse-Tree ----------------------------------------------------------------------//
                                                                                              //------------------------------------------------------------//
                                                                                              //-------------------COMMENTS COLUMN--------------------------//
                                                                                              //------------------------------------------------------------//
  public ParseTreeNode generateParseTree(ContextFreeGrammar cfg, Word w) {                    //        
    mainLogic(cfg, w);                                                                        // Running the main method, created above to get all necessary information for the parse tree generation.
    if (!finalResult) {                                                                       // Checking if the given string is in the language (we have establised the answer (True/False) from the main method).
       return null;                                                                           // Returning nothing if the string is not in the language (finalResult = False).
    }                                                                                         //
    if (w.equals(emptyWord)) {                                                                // Checking if the given string is the empty string and
      return ParseTreeNode.emptyParseTree(cfg.getStartVariable());                            // using method (special case) already created in the ParseTreeNode class.
    }                                                                                         //
                                                                                              //
    Stack<ParseTreeNode> parseTreeStack = new Stack<>();                                      // Creating a new stack. Last in - first out will be used for the parse tree generation.
                                                                                              //
    Derivation fSolution = classDerivations.get(0);                                           // Getting the first solution which was stored in the classDerivation Array List in the main methof (line 99).
    Iterator<Step> revSolution = fSolution.iterator();                                        // Using iterator interface to get to the reverse solution. I really had to google this one. Spent minimum 5 hours to, 
                                                                                              // make it work.
    while (revSolution.hasNext()) {                                                           // Final string should be made of only a single children.
      Step incStep = revSolution.next();                                                      //
      Rule incRule = incStep.getRule();                                                       //
      if (incRule == null) {                                                                  // Checking if the rule is empty.
         break;                                                                               // If that is the case we break out the while loop.
      }                                                                                       //
      Variable LHS = incRule.getVariable();                                                   // Getting the variable from the LHS.
      Word RHS = incRule.getExpansion();                                                      // Getting the expnasion (RHS) of the above variable.
      if (isTerms(cfg, RHS))  {                                                               // Checking if he RHS includes only terminals.
        ParseTreeNode tempNode = (new ParseTreeNode(RHS.get(0)));                             // If the above check is True that means that the RHS is a terminal and so just a single symbol.
        parseTreeStack.push(new ParseTreeNode(LHS, tempNode));                                //  Pushing the new ParseTreeNode in the stack.
      }                                                                                       //
      else if (RHS.length() > 1) {                                                            // Checking if the length of RHS is great that 1 (2 children).  
        parseTreeStack.push(new ParseTreeNode(LHS, parseTreeStack.pop(), parseTreeStack.pop()));// Pushing the new ParseTreeNode in the stack.
      }                                                                                       //
    }                                                                                         //
    return parseTreeStack.pop();                                                              // Returning parse tree elements pushed in our stack.
  }                                                                                           //
//------------------------------------------------------End- Task-2-Generating-Parse-Tree ------------------------------------------------------------------//

//-------------------------------------------------Appendix-Other-Methods ------------------------------------------------------//
                                                                  //------------------------------------------------------------//
                                                                  //-------------------COMMENTS COLUMN--------------------------//
                                                                  //------------------------------------------------------------//
  public boolean isTerms(ContextFreeGrammar cfg, Word w) {        // Method 1: checking if a given a string only consists of terminals and not variables.
    Set<Terminal> terminals = cfg.getTerminals();                 // Getting all the terminals provided for this cfg.             
    for (Symbol i:w) {                                            // Running through all character of given string w.
      if (!terminals.contains(i)) {                               // If at least one character of the string is not included in the list of terminals,
        return false;                                             // return False to main method without executing the rest of the method.
      }                                                           //
    }                                                             // Otherwise,
    return true;                                                  // returning True as the given string only consists of terminals.
  }                                                               //
                                                                  //                                                               
  public boolean isInLanguage(ContextFreeGrammar cfg, Word w) {   // Method 2: checking if the given string is in language.
    mainLogic(cfg, w);                                            // Running the main logic to set class attributes.
    return finalResult;                                           // Returning the outcome (False/True).
  }                                                               //
//-------------------------------------------------End-Appendix-Other-Methods --------------------------------------------------//

}
//------------------------------------------------------------------------------------------------------------------------------//
//---------------------------------------End-of-Parser-Coursework-Algorighm-1-BF------------------------------------------------//
//------------------------------------------------------------------------------------------------------------------------------//