package com.example.elric.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        int[] list = new int[]{5, 1, 2, 3, 5, 66, 77, 88, 99, 123, 567, 321, 424, 554, 332, 221, 112, 332, 234, 123, 980};
        long a = System.currentTimeMillis();

        Log.e("asd", System.currentTimeMillis() - a + " time : " + System.currentTimeMillis());

        QuikSort(list, 0, list.length - 1);
        Log.e("asd", System.currentTimeMillis() - a + " time : " + System.currentTimeMillis());
        for (int i = 0; i < list.length; i++) {
            Log.e("asd", list[i] + "");

        }


        int[] list2 = new int[]{5, 1, 2, 3, 5, 66, 77, 88, 99, 123, 567, 321, 424, 554, 332, 221, 112, 332, 234, 123, 980};
        long a1 = System.currentTimeMillis();

        Log.e("asd", System.currentTimeMillis() - a1 + " time : " + System.currentTimeMillis());

        sort(list2, 0, list2.length - 1);
        Log.e("asd", System.currentTimeMillis() - a1 + " time : " + System.currentTimeMillis());
        for (int i = 0; i < list2.length; i++) {
            Log.e("asd", list2[i] + "");

        }

    }

    public void sort(int arr[], int low, int high) {
        int l = low;
        int h = high;
        int povit = arr[low];

        while (l < h) {
            while (l < h && arr[h] >= povit)
                h--;
            if (l < h) {
                int temp = arr[h];
                arr[h] = arr[l];
                arr[l] = temp;
                l++;
            }
            while (l < h && arr[l] <= povit)
                l++;

            if (l < h) {
                int temp = arr[h];
                arr[h] = arr[l];
                arr[l] = temp;
                h--;
            }
        }
//        System.out.print(arr);
//        Log.e("asd", "l=" + (l + 1) + "h=" + (h + 1) + "povit=" + povit + "\n");
        if (l > low) sort(arr, low, l - 1);
        if (h < high) sort(arr, l + 1, high);
    }


    void QuikSort(int arr[], int low, int high) {
        if (low >= high) {    //递归退出条件：只有一个元素时
            return;
        }
        int pivot = arr[low];
        int i = low;
        for (int j = low + 1; j <= high; j++) {
            if (arr[j] <= pivot) {        //a[j] is smaller than pivot
                i++;    //a[i] is bigger than pivot
                if (i != j) {
//                    Swap(arr[i], arr[j]);
                    int temp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = temp;
                }
            }
        }
//        Swap(arr[low], arr[i]);    //Swap pivot to middle position
        int temp = arr[i];
        arr[i] = arr[low];
        arr[low] = temp;

        //进行分化(partition),递归
        QuikSort(arr, low, i - 1);        //a[i] is the pivot now
        QuikSort(arr, i + 1, high);
    }


    void Swap(int a, int b) {
        int temp = a;
        a = b;
        b = temp;
    }

}
