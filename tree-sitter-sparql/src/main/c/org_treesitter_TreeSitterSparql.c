
#include <jni.h>
void *tree_sitter_sparql();
/*
 * Class:     org_treesitter_TreeSitterSparql
 * Method:    tree_sitter_sparql
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterSparql_tree_1sitter_1sparql
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_sparql();
}
