
#include <jni.h>
void *tree_sitter_scss();
/*
 * Class:     org_treesitter_TreeSitterScss
 * Method:    tree_sitter_scss
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterScss_tree_1sitter_1scss
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_scss();
}
