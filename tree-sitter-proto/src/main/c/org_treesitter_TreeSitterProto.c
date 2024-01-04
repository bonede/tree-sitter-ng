
#include <jni.h>
void *tree_sitter_proto();
/*
 * Class:     org_treesitter_TreeSitterProto
 * Method:    tree_sitter_proto
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterProto_tree_1sitter_1proto
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_proto();
}
