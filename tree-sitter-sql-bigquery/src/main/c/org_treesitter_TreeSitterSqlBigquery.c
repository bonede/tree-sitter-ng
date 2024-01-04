
#include <jni.h>
void *tree_sitter_sql_bigquery();
/*
 * Class:     org_treesitter_TreeSitterSqlBigquery
 * Method:    tree_sitter_sql_bigquery
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterSqlBigquery_tree_1sitter_1sql_1bigquery
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_sql_bigquery();
}
