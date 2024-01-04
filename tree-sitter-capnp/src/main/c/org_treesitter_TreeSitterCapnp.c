
#include <jni.h>
void *tree_sitter_capnp();
/*
 * Class:     org_treesitter_TreeSitterCapnp
 * Method:    tree_sitter_capnp
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterCapnp_tree_1sitter_1capnp
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_capnp();
}
