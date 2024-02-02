// ignore_for_file: body_might_complete_normally_nullable
import 'package:flutter/foundation.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:google_sign_in/google_sign_in.dart';

class FirebaseService {
  final FirebaseAuth _firebaseAuth = FirebaseAuth.instance;
  final GoogleSignIn _googleSignIn = kIsWeb
      ? GoogleSignIn(clientId: '286196859062-nbjutn1om6957qqbfkj5crp5plqd9fut.apps.googleusercontent.com')
      : GoogleSignIn();

  Future<String?> signInWithGoogle() async {
    
    try {
      final GoogleSignInAccount? googleSignInAccount =
          await _googleSignIn.signIn();

      final GoogleSignInAuthentication googleSignInAuthentication =
          await googleSignInAccount!.authentication;

      final AuthCredential authCredential = GoogleAuthProvider.credential(
          accessToken: googleSignInAuthentication.accessToken,
          idToken: googleSignInAuthentication.idToken);

      await _firebaseAuth.signInWithCredential(authCredential);
    } on FirebaseAuthException catch (e) {
      debugPrint(e.message);
      //throw e;
    }
  }

  Future<void> signOutFromGoogle() async {
    await _googleSignIn.signOut();
    await _firebaseAuth.signOut();
  }
}
