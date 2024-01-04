
#include <jni.h>
void *tree_sitter_qmljs();
/*
 * Class:     org_treesitter_TreeSitterQmljs
 * Method:    tree_sitter_qmljs
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterQmljs_tree_1sitter_1qmljs
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_qmljs();
}
