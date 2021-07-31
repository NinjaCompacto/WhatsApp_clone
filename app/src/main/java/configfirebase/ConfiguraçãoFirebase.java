package configfirebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguraçãoFirebase {

    private static DatabaseReference databaseReference;
    private static FirebaseAuth auth;

    //retonar a instancia do FirebaseDatabase
    public static DatabaseReference getDatabaseReference(){
            if (databaseReference == null){
                databaseReference = FirebaseDatabase.getInstance().getReference();
            }
            return databaseReference;
    }

    //retorna a instancia do FirebaseAuth
    public static  FirebaseAuth getAuth (){
            if (auth == null){
                auth = FirebaseAuth.getInstance();
            }
            return auth;
    }
}
