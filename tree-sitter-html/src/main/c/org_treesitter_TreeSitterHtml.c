
#include <jni.h>
void *tree_sitter_html();
/*
 * Class:     org_treesitter_TreeSitterHtml
 * Method:    tree_sitter_html
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterHtml_tree_1sitter_1html
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_html();
}
