
#include <jni.h>
void *tree_sitter_objc();
/*
 * Class:     org_treesitter_TreeSitterObjc
 * Method:    tree_sitter_objc
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterObjc_tree_1sitter_1objc
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_objc();
}
