
#include <jni.h>
void *tree_sitter_dot();
/*
 * Class:     org_treesitter_TreeSitterDot
 * Method:    tree_sitter_dot
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterDot_tree_1sitter_1dot
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_dot();
}
