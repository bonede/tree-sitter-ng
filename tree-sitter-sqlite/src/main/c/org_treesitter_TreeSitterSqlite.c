
#include <jni.h>
void *tree_sitter_sqlite();
/*
 * Class:     org_treesitter_TreeSitterSqlite
 * Method:    tree_sitter_sqlite
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterSqlite_tree_1sitter_1sqlite
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_sqlite();
}
