
#include <jni.h>
void *tree_sitter_css();
/*
 * Class:     org_treesitter_TreeSitterCss
 * Method:    tree_sitter_css
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterCss_tree_1sitter_1css
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_css();
}
