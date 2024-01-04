
#include <jni.h>
void *tree_sitter_graphql();
/*
 * Class:     org_treesitter_TreeSitterGraphql
 * Method:    tree_sitter_graphql
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterGraphql_tree_1sitter_1graphql
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_graphql();
}
