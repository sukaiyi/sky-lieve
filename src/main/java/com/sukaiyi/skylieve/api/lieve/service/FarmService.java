package com.sukaiyi.skylieve.api.lieve.service;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface FarmService {


    @Note("农场详细状态")
    @FormUrlEncoded
    @POST("v1/game/farm/panorama")
    Call<String> panorama(
			@Field("visit_farm_id") String visit_farm_id,
			@Field("obstacle_num") String obstacle_num
	);

    @Note("捡宝箱")
    @FormUrlEncoded
    @POST("v1/game/farm/giftbox/open")
    Call<String> openGiftBox(
			@Field("rainbow_coin") String rainbow_coin
	);

    @Note("删除要彩虹币的宝箱")
    @POST("v1/game/farm/giftbox/del")
    Call<String> delGiftBox();

    @Note("看广告领奖励")
    @FormUrlEncoded
    @POST("v1/game/farm/ad-video/view")
    Call<String> freePickUp(
			@Field("play_id") int item_id
	);

    @Note("正在打广告的卖家")
    @POST("v1/game/farm/ad/query")
    Call<String> adQuery();

    @Note("帮助别人复活树子")
    @FormUrlEncoded
    @POST("v1/game/farm/tree/revive")
    Call<String> reviveTree(
			@Field("id") String id,
			@Field("revive_farm_id") String revive_farm_id
	);

    @Note("复活别个农场的花丛")
    @FormUrlEncoded
    @POST("v1/game/farm/flowerbush/revive")
    Call<String> reviveFlower(
			@Field("id") String id,
			@Field("revive_farm_id") String revive_farm_id
	);

    @Note("帮别个装轮船")
    @FormUrlEncoded
    @POST("v1/game/farm/cruiseship/containerize")
    Call<String> loadShip(
			@Field("containerized_farm_id") String containerized_farm_id,
			@Field("ship_id") String ship_id,
			@Field("box_id") String box_id,
			@Field("by_rainbow_coin") String by_rainbow_coin
	);

    @Note("帮别个装火车")
    @FormUrlEncoded
    @POST("v1/game/farm/train/containerize")
    Call<String> loadTrain(
			@Field("containerized_farm_id") String containerized_farm_id,
			@Field("slot") String slot,
			@Field("rainbow_coin") String rainbow_coin
	);

    @Note("收获工厂产品")
    @FormUrlEncoded
    @POST("v1/game/farm/factory/harvest")
    Call<String> factoryHarvest(
			@Field("factory_id") String factory_id,
			@Field("product_ids") String product_ids,
			@Field("rainbow_coin") String rainbow_coin
	);

    @Note("工厂生产产品")
    @FormUrlEncoded
    @POST("v1/game/farm/factory/produce")
    Call<String> produce(
			@Field("factory_id") String factory_id,
			@Field("item_id") String item_id
	);

    @Note("在路边摊上架商品")
    @FormUrlEncoded
    @POST("v1/game/farm/stall/onshelf")
    Call<String> onShelf(
			@Field("item_id") String item_id,
			@Field("coin") int coin,
			@Field("ad") int ad,
			@Field("slot") int slot,
			@Field("count") int count,
			@Field("rainbow_coin") int rainbow_coin
	);

    @Note("我们的动态")
    @POST("v1/game/farm/operations/query")
    Call<String> queryOperations();

    @Note("购买seller_farm_id的stall_sale_id商品")
    @FormUrlEncoded
    @POST("v1/game/farm/stall/buy")
    Call<String> stallBuy(
			@Field("seller_farm_id") String seller_farm_id,
			@Field("stall_sale_id") String stall_sale_id
	);

    @Note("收庄稼")
    @FormUrlEncoded
    @POST("v1/game/farm/crops/harvest")
    Call<String> cropsHarvest(
			@Field("item_id") String item_id,
			@Field("farmland_ids") String farmland_ids
	);

    @Note("种庄稼")
    @FormUrlEncoded
    @POST("v1/game/farm/crops/plant")
    Call<String> cropsPlant(
			@Field("item_id") String item_id,
			@Field("farmland_ids") String farmland_ids
	);

    @Note("收蜂蜜")
    @FormUrlEncoded
    @POST("v1/game/farm/beehive/harvest")
    Call<String> beehiveHarvest(
			@Field("item_id") String item_id,
			@Field("beehive_id") String beehive_id
	);

    @Note("收获动物产品")
    @FormUrlEncoded
    @POST("v1/game/farm/animals/harvest")
    Call<String> animalsHarvest(
			@Field("item_id") String item_id,
			@Field("yards_info") String yards_info
	);

    @Note("喂养动物")
    @FormUrlEncoded
    @POST("v1/game/farm/animals/feed")
    Call<String> animalsFeed(
			@Field("item_id") String item_id,
			@Field("yards_info") String yards_info
	);

    @Note("为路边摊的商品创建广告")
    @FormUrlEncoded
    @POST("v1/game/farm/stall/ad")
    Call<String> stallAd(
			@Field("stall_sale_id") String stall_sale_id
	);

	@Note("切换建筑的状态（0显示 or 1收起来）")
	@FormUrlEncoded
	@POST("v1/game/farm/building/switch")
	Call<String> buildingSwitch(
			@Field("building_id") String building_id,
			@Field("item_id") String item_id,
			@Field("new_status") String new_status
	);

	@Note("购买建筑")
	@FormUrlEncoded
	@POST("v1/game/farm/building/add")
	Call<String> buildingAdd(
			@Field("item_id") String item_id,
			@Field("x") String x,
			@Field("y") String y,
			@Field("rotate") String rotate
	);

	@Note("向小兔子售卖物品")
	@FormUrlEncoded
	@POST("/v1/game/farm/npc/order/complete")
	Call<String> npcOrderComplete(
			@Field("order_id") String order_id,
			@Field("rainbow_coin") String rainbow_coin
	);


}
