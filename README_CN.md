# McMediaLoader

## [English][Doc_English.link] | 中文文档

**McMediaLoader** 是一款Android工具库，让读取Android设备本地存储的多媒体文件（图片、视频）变得简单。另外，它提供了让你自定义多媒体文件排序、分组的简单方法。

## 配置

### 步骤 1.

添加下列的Gradle配置到你的Android项目。在你的根目录 `build.gradle`文件中：

```
allprojects {
    repositories {
        maven {
            url "https://wzhdev.bintray.com/github"
        }
    }
}
```

### 步骤 2.

将依赖添加到你的 app `build.gradle` 文件：

```
implementation 'com.windyziheng:McMediaLoader:1.0.2'
```

### 步骤 3.

你需要自行在`AndroidManifest.xml`中配置并请求下列权限：

```
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

***现在你就可以在你的项目中使用 McMediaLoader 了。 :)***

------------

## 简单使用方法

你仅需创建默认的[MediaLoader][MediaLoader.java]病情设置监听器来监听结果回调就可以了。

```
MediaLoader.createDefaultMediaLoader(this)
	.setOnProcessListener(new MediaLoader.OnProcessListener<MediaEntity>() {
		@Override
		public void onLoadMediaStart() {
			...
		}

		@Override
		public void onLoadMediaDone(MediaLoaderResult<MediaEntity> result) {
			boolean isSuccess = result.isSuccess();
			QueryResult<MediaEntity> queryResult = result.getQueryResult();
			GroupResult<MediaEntity> groupResult = result.getGroupResult();
			...
		}
	})
	.runLogic();
```

调用`MediaLoader`中的`runlogic()`，McMediaLoader就会从内部存储中检索多媒体文件，然后将结果进行排序和分组，之后你在`MediaLoaderResult`中获取[QueryResult][QueryResult.java]和[GroupResult][GroupResult.java]。通常情况下，`GroupResult`中的信息已经足够你使用了。

## 自定义使用方法

- **自定义检索和分组过程**

调用`MediaLoader.create(Provider provider)`方法来创建`MediaLoader`，并自行将它与[Querier][MediaQuerier.java]和[Grouper][MediaGrouper.java]进行绑定，如下所示：

```
MediaLoader mediaLoader = MediaLoader.create(new MediaLoader.Provider<MediaEntity>() {
	@Override
	public MediaQuerier.Querier<MediaEntity> provideQuerier() {
		return MediaQuerier.createDefaultQuerier(context, QueryType.All);
//		return MediaQuerier.createQuerier(queryFactory)
//				.setQuerySortMediaIsAction(true)
//				.setQuerySortMediaRule(new ModifiedDateRule<>(false));
			}

	@Override
	public MediaGrouper.Grouper<MediaEntity> providerGrouper() {
		return MediaGrouper.createDefaultGrouper();
//		return MediaGrouper.createGrouper(MediaGrouper.createDefaultFactory())
//				.setGroupSortMediaIsAction(true)
//				.setGroupSortMediaRule(new ModifiedDateRule<>(true))
//				.setSortDirGroupIsAction(true)
//				.setSortDirGroupRule(new KeyNameRule<>(true))
//				.setSortCustomGroupIsAction(true)
//				.setSortCustomGroupRule(new ListSizeRule<>(false));
	}
});
```

- **添加自定义分组**

这是一个使用自定义规则来创建分组的例子，代码如下所示：

```
CustomRule<MediaEntity> paginateRule = new CustomRule<MediaEntity>("paginate") {

	private static final int PAGE_SIZE = 100;

	@Override
	public List<GroupEntity<MediaEntity>> createGroups(@NonNull List<MediaEntity> mediaList) {
		List<GroupEntity<MediaEntity>> list = new ArrayList<>();
		for (int i = 0; i < mediaList.size() / PAGE_SIZE + 1; i++) {
			List<MediaEntity> medias = new ArrayList<>();
			int start = i * PAGE_SIZE;
			int end = Math.min((i + 1) * PAGE_SIZE, mediaList.size());
			String key = (start + 1) + " -- " + end;
			for (int j = start; j < end; j++) {
				medias.add(mediaList.get(j));
			}
			GroupEntity<MediaEntity> group = new GroupEntity<>(GroupType.Custom, key, key, medias);
			if (group.isAvailable()) {
				list.add(group);
			}
		}
		return list;
	}
};
GroupFactory<MediaEntity> groupFactory = new GroupFactory.Creator<>()
	.setIsGroupByType(true)
	.setIsGroupByDir(true)
	.addCustomRule(paginateRule)
	.create();
MediaGrouper.Grouper<MediaEntity> grouper =  MediaGrouper.createGrouper(groupFactory);
```

然后，向`自定义检索和分组过程`中一样将`MeidaLoader`和`grouper`绑定。

- **自定义检索器**

你可以自行创建继承于[MediaEntity][MediaEntity.java]的实体类并且自定义检索器，例如：

```
public class MImage extends MediaEntity{
	protected MImage(String path, long modifiedDate, int size, String mimeType, Size resolution) {
		super(MediaType.Image, path, modifiedDate, size, mimeType, resolution);
	}
}

QueryFactory<MImage> queryFactory = new QueryFactory<MImage>() {
	@Override
	protected boolean onSetup() {
		...
		return true;
	}

	@Override
	protected List<MImage> onQuery() {
		List<MImage> result = new ArrayList<>();
		...
		return result;
	}

	@Override
	protected void onRelease() {
		...
	}
};
MediaQuerier.Querier<MImage> querier = MediaQuerier.createQuerier(queryFactory);
```

如果你觉得自定义很复杂，你可以将[DefaultQueryFactory][DefaultQueryFactory.java]作为参考哦。

然后，向`自定义检索和分组过程`中一样将`MeidaLoader`和`querier`绑定。

更多相关信息，劳烦自行查看我的代码和注释。:)

## 联系我

**Email :** windy649457889@gmail.com

**QQ :** **2862908118**

## 开源许可

```
Copyright © 2020 WangZiheng

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[Doc_English.link]: https://github.com/windyziheng/McMediaLoader/blob/master/README.md
[Doc_Chinese.link]: https://github.com/windyziheng/McMediaLoader/blob/master/README_CN.md

[MediaLoader.java]: https://github.com/windyziheng/McMediaLoader/blob/master/lib/src/main/java/com/windyziheng/mcmedialoader/core/MediaLoader.java
[QueryResult.java]: https://github.com/windyziheng/McMediaLoader/blob/master/lib/src/main/java/com/windyziheng/mcmedialoader/entity/result/QueryResult.java
[GroupResult.java]: https://github.com/windyziheng/McMediaLoader/blob/master/lib/src/main/java/com/windyziheng/mcmedialoader/entity/result/GroupResult.java
[MediaQuerier.java]: https://github.com/windyziheng/McMediaLoader/blob/master/lib/src/main/java/com/windyziheng/mcmedialoader/core/MediaQuerier.java
[MediaGrouper.java]: https://github.com/windyziheng/McMediaLoader/blob/master/lib/src/main/java/com/windyziheng/mcmedialoader/core/MediaGrouper.java
[MediaEntity.java]: https://github.com/windyziheng/McMediaLoader/blob/master/lib/src/main/java/com/windyziheng/mcmedialoader/entity/media/MediaEntity.java
[DefaultQueryFactory.java]: https://github.com/windyziheng/McMediaLoader/blob/master/lib/src/main/java/com/windyziheng/mcmedialoader/query/DefaultQueryFactory.java