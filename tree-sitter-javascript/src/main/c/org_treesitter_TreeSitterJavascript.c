
#include <jni.h>
void *tree_sitter_javascript();
/*
 * Class:     org_treesitter_TreeSitterJavascript
 * Method:    tree_sitter_javascript
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterJavascript_tree_1sitter_1javascript
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_javascript();
}
