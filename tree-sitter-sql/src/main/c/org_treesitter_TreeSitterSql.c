
#include <jni.h>
void *tree_sitter_sql();
/*
 * Class:     org_treesitter_TreeSitterSql
 * Method:    tree_sitter_sql
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterSql_tree_1sitter_1sql
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_sql();
}
