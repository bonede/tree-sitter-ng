
#include <jni.h>
void *tree_sitter_scheme();
/*
 * Class:     org_treesitter_TreeSitterScheme
 * Method:    tree_sitter_scheme
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterScheme_tree_1sitter_1scheme
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_scheme();
}
