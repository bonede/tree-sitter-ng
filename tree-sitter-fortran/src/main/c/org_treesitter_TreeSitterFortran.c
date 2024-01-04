
#include <jni.h>
void *tree_sitter_fortran();
/*
 * Class:     org_treesitter_TreeSitterFortran
 * Method:    tree_sitter_fortran
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterFortran_tree_1sitter_1fortran
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_fortran();
}
