#include <jni.h>
void *tree_sitter_soql();
/*
 * Class:     org_treesitter_TreeSitterSoql
 * Method:    tree_sitter_soql
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterSoql_tree_1sitter_1soql
  (JNIEnv *env, jclass clz){
  return (jlong) tree_sitter_soql();
}
