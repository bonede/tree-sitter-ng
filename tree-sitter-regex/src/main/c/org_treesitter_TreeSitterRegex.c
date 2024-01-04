
#include <jni.h>
void *tree_sitter_regex();
/*
 * Class:     org_treesitter_TreeSitterRegex
 * Method:    tree_sitter_regex
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterRegex_tree_1sitter_1regex
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_regex();
}
