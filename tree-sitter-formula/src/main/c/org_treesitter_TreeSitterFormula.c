
#include <jni.h>
void *tree_sitter_formula();
/*
 * Class:     org_treesitter_TreeSitterFormula
 * Method:    tree_sitter_formula
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterFormula_tree_1sitter_1formula
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_formula();
}
