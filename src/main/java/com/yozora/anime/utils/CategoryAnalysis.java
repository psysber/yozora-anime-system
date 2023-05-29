package com.yozora.anime.utils;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public class CategoryAnalysis {
    Map<String, List<String>> dict=new HashMap<>();
    List<String> fantasy = Arrays.asList("魔法", "史诗", "魔法师", "龙", "神话", "中世纪", "吸血鬼", "精灵", "魔法森林", "魔法戒指", "魔法杖", "剑", "骑士", "魔法药水", "幽灵", "鬼怪", "魔法防御", "亡灵", "魔法物品", "鬼火");
    List<String> magical = Arrays.asList("魔法", "魔法道具", "魔法棒", "魔法变身", "女孩", "童话", "魔法师", "魔法世界", "魔法密林", "魔女", "鬼魂", "黑暗力量", "仙女", "小精灵", "萌", "变身", "魔法公主", "魔法战士");
    List<String>sciFi=Arrays.asList("星际旅行", "超能力", "未来社会", "外星种族", "时间旅行", "机器人", "太空动作", "虚拟现实", "宇宙冒险", "克隆人", "黑暗宇宙", "科技侦探", "未来战争", "变形金刚", "异次元世界", "高科技", "生化危机", "电子竞技", "无人机", "探险"                              );
    List<String>mecha=Arrays.asList("战斗机", "机甲装备", "光束武器", "巨大机器人", "毁灭兵器", "转型机器人", "机械维修", "机器人研究", "防卫战斗", "空天武士", "机体精神力", "钢铁新兵", "机兽奥特曼", "全息影像", "宇宙漂流", "移动之城", "铃铛Robotto", "钢骨霸王", "星球驾驶员", "铁拳战士");
    List<String>adventure=Arrays.asList("远古遗迹", "丛林探险", "宝藏寻找", "生存挑战", "稀世奇珍", "蒸汽朋克", "急速飞行", "海盗旗帜", "幻想国度", "奇幻旅程", "心理探险", "神秘岛屿", "远航探测", "梦幻之旅", "辐射荒原", "超自然力量", "神秘部落", "沙漠征途", "探险家", "龟派冒险");
    List<String>school=Arrays.asList("青涩校园", "班级生活", "学生会长", "传统文化", "老师学生恋", "体育俱乐部", "选举竞争", "春游研修", "男女关系", "模拟人生", "后宫爱情", "家庭作业", "课外辅导", "海豚音乐会", "医学界磨炼", "温馨笑剧", "学术竞赛", "草根奋斗", "职业规划", "快乐班级");
    List<String>remance=Arrays.asList("初恋经历", "远距相思", "婚姻家庭", "亲密接触", "异地恋情", "多角恋爱", "青葱岁月", "妒忌纠纷", "难言告白", "同居生活", "开放婚姻", "感情升华", "年龄差异", "心动时刻", "单身狗日常", "浪漫约会", "缘分撮合", "爱的承诺", "失恋舟曲", "弃爱转身");
    List<String>gods=Arrays.asList("秘密爱情", "契约关系", "好友恋爱", "原始欲望", "天真纯情", "初吻心动", "苦恋痴迷", "引爆激情", "性别误会", "暗恋对象", "喜怒哀乐", "开花结果", "火辣奇缘", "情感纠结", "感情博弈", "得之必失", "我愿等你");
    List<String> buttle=Arrays.asList( "战争", "冲锋", "抗击", "奋斗", "攻击", "防御", "突围", "逆袭", "破釜沉舟", "挑战",
            "集结", "战斗力", "牺牲", "胜利", "失败", "英勇", "魄力", "爆发", "磨砺", "竞技");
    List<String> horror=Arrays.asList("恐怖", "鬼魂", "妖怪", "病毒", "诡异", "暴力", "杀戮", "丧尸", "幽灵", "噩梦",
            "血腥", "沉重", "恶心", "毁灭", "绝望", "危险", "险恶", "灵异", "折磨", "地狱");
    List<String> reasoning = Arrays.asList("推理", "侦探", "嫌疑人", "破案", "悬疑", "证据", "线索", "犯罪", "完美犯罪", "谜题",
            "解密", "智斗", "死亡游戏", "推论", "独立思考", "神秘", "信息收集", "诈骗", "真相大白", "指纹");
    List<String> suspense = Arrays.asList("悬疑", "推理", "神秘", "犯罪", "解谜", "谜团", "嫌疑人", "法律", "凶手",
            "证据", "线索", "暴力", "心理", "报复", "失踪", "诡异", "杀人", "特工", "安全", "持续");
    List<String> crime = Arrays.asList("犯罪", "破案", "刑事", "侦探", "嫌疑人", "司法", "行动", "警察",
            "法律", "盗窃", "骗局", "杀人", "暴力", "绑架", "诈骗", "团伙", "安全", "犯罪心理", "毒品", "侵犯");
    List<String> inspirational = Arrays.asList("励志", "成功", "勇气", "自信", "毅力", "坚持", "改变", "横跨界限",
            "不屈不挠", "实现梦想", "信念", "坚定", "成长", "自我超越", "感恩", "创新", "肯定", "大胆冒险",
            "理想", "坚韧");
    List<String> humor = Arrays.asList("搞笑", "笑话", "小品", "讽刺", "滑稽", "特技", "幽默", "爆笑",
            "手法", "脑洞", "卖萌", "插科打诨", "古灵精怪", "鬼畜", "恶搞", "模仿", "调侃", "开心果",
            "超级搞笑王", "笑话大全");
    List<String> animeHarem = Arrays.asList("后宫", "校园爱情", "美少女", "修行", "魔法", "女战士", "战斗", "冒险",
            "搞笑", "神话", "同居", "传说", "幻想", "萌系", "异世界", "神秘力量", "人鱼", "音乐",
            "双重身份", "美味料理");
    List<String> yuri = Arrays.asList("百合", "少女", "暗恋", "纯爱", "友情", "经典",
            "同居", "励志", "青春", "校园", "百合之花", "天真烂漫", "甜蜜", "神仙",
            "工作", "非日常", "潮湿", "恶作剧", "柔软", "暖心");
    List<String> animeWork = Arrays.asList("工薪族", "魔法师", "老板", "创业", "梦想", "职场",
            "经理", "冲突", "竞争", "作家", "社交", "营销", "就业", "画家",
            "厨师", "摄影师", "医生", "律师", "秘书", "程序员");
    List<String> yaoi = Arrays.asList("Yaoi", "女性向", "BL", "耽美", "柔情", "长篇",
            "短篇", "年下攻", "年上攻", "纯爱", "百合", "出轨", "正太", "傲娇", "打脸", "迷幻", "开放", "神秘", "魔幻");


    CategoryAnalysis(HashMap<String,List<String>> hashMap){
        dict=hashMap;
    }
    public String analysis(String content){
        if(StringUtils.isEmpty(content)){
            return "Unknown";
        }
        Segment segment = HanLP.newSegment();
        content = content.toLowerCase();
        Map<String, Integer> contentVector = new HashMap<>();
        for (Term term : segment.seg(content)) {
            String word = term.word.toLowerCase();
            contentVector.put(word, contentVector.getOrDefault(word, 0) + 1);
        }
        String bestMatch = "";
        double highestSimilarity = 0.0;
        for (Map.Entry<String, List<String>> entry : dict.entrySet()) {
            String category = entry.getKey();
            List<String> keywords = entry.getValue();
            String combinedKeywords = String.join(" ", keywords).toLowerCase();
            Map<String, Integer> categoryVector = new HashMap<>();
            for (Term term : segment.seg(combinedKeywords)) {
                String word = term.word.toLowerCase();
                categoryVector.put(word, categoryVector.getOrDefault(word, 0) + 1);
            }
            double similarity = cosineSimilarity(contentVector, categoryVector);
            if (similarity > highestSimilarity) {
                highestSimilarity = similarity;
                bestMatch = category;
            }
        }
        return bestMatch.isEmpty() ? "Unknown" : bestMatch;
    }


    private double cosineSimilarity(Map<String, Integer> vector1, Map<String, Integer> vector2) {
        double dotProduct = 0.0;
        for (String token : vector1.keySet()) {
            if (vector2.containsKey(token)) {
                dotProduct += vector1.get(token) * vector2.get(token);
            }
        }
        double mag1 = magnitude(vector1.values());
        double mag2 = magnitude(vector2.values());
        return dotProduct / (mag1 * mag2);
    }

    private double magnitude(Collection<Integer> values) {
        double sumOfSquares = 0.0;
        for (int value : values) {
            sumOfSquares += value * value;
        }
        return Math.sqrt(sumOfSquares);
    }

}

