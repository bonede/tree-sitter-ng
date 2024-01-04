
#include <jni.h>
void *tree_sitter_python();
/*
 * Class:     org_treesitter_TreeSitterPython
 * Method:    tree_sitter_python
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterPython_tree_1sitter_1python
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_python();
}
