
#include <jni.h>
void *tree_sitter_ocaml();
/*
 * Class:     org_treesitter_TreeSitterOcaml
 * Method:    tree_sitter_ocaml
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterOcaml_tree_1sitter_1ocaml
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_ocaml();
}
