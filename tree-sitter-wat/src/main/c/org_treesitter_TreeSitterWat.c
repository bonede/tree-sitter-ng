
#include <jni.h>
void *tree_sitter_wat();
/*
 * Class:     org_treesitter_TreeSitterWat
 * Method:    tree_sitter_wat
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterWat_tree_1sitter_1wat
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_wat();
}
