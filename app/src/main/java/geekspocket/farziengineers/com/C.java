package geekspocket.farziengineers.com;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geekspocket.farziengineers.com.geekspocket.R;
import static android.support.v7.widget.RecyclerView.Adapter;
import static android.support.v7.widget.RecyclerView.LayoutManager;

public class C extends Fragment {

    View view;
    private RecyclerView recyclerView;
    LayoutManager layoutManager;
    RecyclerAdapter adapter;
    List<Data> dataList;
    FirebaseFirestore firebaseFirestore;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        dataList = new ArrayList<>();

        FirebaseApp.initializeApp(getContext());
        firebaseFirestore = FirebaseFirestore.getInstance();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new RecyclerAdapter(getActivity(),dataList);
        recyclerView.setAdapter(adapter);

        firebaseFirestore.collection("C")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){

                            String keyword = documentSnapshot.getString("keyword");
                            String keywordDefination = documentSnapshot.getString("Defination");
                            String languageLogo = documentSnapshot.getString("logo");
                            String keywordExample = documentSnapshot.getString("keywordExample");
                            dataList.add(new Data(keyword,keywordDefination, languageLogo,keywordExample));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
        return view;
    }
}
