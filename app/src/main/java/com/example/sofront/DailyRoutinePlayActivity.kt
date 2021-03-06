package com.example.sofront

import android.app.Activity
import android.app.Instrumentation
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.sofront.databinding.ActivityDailyRoutinePlayBinding

class DailyRoutinePlayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDailyRoutinePlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val workoutName = binding.workoutName //운동이름
        val workoutBar = binding.workoutBar //진행바
        val workoutSet = binding.workoutSet
        val playBtn = binding.playBtn //세트진행 버튼
        val nextBtn = binding.nextBtn //다음세트 버튼
        val help = binding.help //응원문구 (세트끝나면 잘했다고 바꿔주기)
        val workoutDetail = binding.workoutDetail

        workoutName.text = intent.getStringExtra("workoutName") //운동 이름 가져오기
//        val currCount = intent.getIntExtra("currCount",10) //총 세트수
//        val currWeight = intent.getIntExtra("currSet",0) //지금 세트수
        val set = (intent.getSerializableExtra("set")) as Set //세트 정보(몇키로, 몇번)
        workoutSet.text = "${(application as WorkoutProgress).getSetCount()+1}세트"
        workoutDetail.text = "${set.weight}kg으로 ${set.count}회 할거에요💪"

        workoutBar.max = set.count
//        workoutBar.progress = nowSet
        var count = 0
        playBtn.setOnClickListener{
            if(count<set.count){
                count++
                playBtn.text = count.toString()+"회"
                workoutBar.progress = count
            }
            if(count==set.count){
                playBtn.setBackgroundColor(resources.getColor(R.color.patel_yellow))
                nextBtn.visibility = View.VISIBLE
                help.text = "잘했다"
            }
        }
        nextBtn.setOnClickListener{
//            val resultIntent = Intent(this,DailyRoutineDetailActivity::class.java)
//            val bundle = Bundle()
//            bundle.putInt("result1",10)
//            resultIntent.putExtras(bundle)
//            resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            resultIntent.putExtra("endSet",1)
//            setResult(Activity.RESULT_OK,resultIntent)
//            startActivity(resultIntent)
            (application as WorkoutProgress).plusSetCount()
            finish()
        }
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("플랜헬퍼")
            .setMessage("정말로 운동을 끝내시겠습니까?.\n현재 운동 횟수는 기록되지 않습니다.")
            .setPositiveButton("확인",
                DialogInterface.OnClickListener { dialog, id ->
                    //확인클릭
                    super.onBackPressed()
                    Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                })
            .setNegativeButton("취소",
                DialogInterface.OnClickListener { dialog, id ->
                    //취소클릭
                })
        // 다이얼로그를 띄워주기
        builder.show()
    }
}