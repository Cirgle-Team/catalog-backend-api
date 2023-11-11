package org.cirgle.catalog.util

class RandomNickGenerator {
    companion object {
        private val advList: List<String> = listOf(
            "고소한", "귀여운", "긍정적인", "깊이있는", "깔끔한",
            "다정한", "달달한", "달콤한", "대담한", "든든한",
            "따뜻한", "맛있는", "매력적인", "멋진", "무뚝뚝한",
            "발랄한", "부드러운", "사랑스러운", "상큼한", "시원한",
            "씁쓸한", "아름다운", "여유로운", "예쁜", "자유로운",
            "재미있는", "조용한", "지혜로운", "진한", "착한",
            "크리미한", "털털한", "포근한", "향긋한", "훌륭한"
        )

        private val nickList: List<String> = listOf(
            "드립커피", "마끼아또", "아메리카노", "아인슈페너", "에스프레소",
            "카페라떼", "카페모카", "카푸치노", "콘파냐", "콜드브루"
        )

        fun generate(): String = "%s %s".format(
            this.advList.random(),
            this.nickList.random()
        )
    }
}