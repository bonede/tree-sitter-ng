
#include <jni.h>
void *tree_sitter_eno();
/*
 * Class:     org_treesitter_TreeSitterEno
 * Method:    tree_sitter_eno
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterEno_tree_1sitter_1eno
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_eno();
}
