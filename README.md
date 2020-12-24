# McMediaLoader

## English | [中文文档][Doc_Chinese.link]

**McMediaLoader** is an extension Android library that make it easy to load Media(Image, Video) from Android device storage. In addition, it offers easy ways for you to customize the sorting and grouping process.

## Setup

### Step 1.

Add the following Gradle configuration to your Android project. In your root `build.gradle` file:

```
allprojects {
    repositories {
        maven {
            url "https://wzhdev.bintray.com/github"
        }
    }
}
```

### Step 2.

Add the dependency to your app `build.gradle` file:

```
implementation 'com.windyziheng:McMediaLoader:1.0.1'
```

### Step 3.

There are permissions that you need to register in your `AndroidManifest.xml` and request them by yourself.

```
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

***Now, you are able to use McMediaLoader in your project. :)***

------------

## Simple Usage

Just create the default [MediaLoader][MediaLoader.java] and set the listener to observe the result and it will be alright.

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

Call `runlogic()` in `MediaLoader` and it will query medias from storage and then group the result, so you are can get [QueryResult][QueryResult.java] and [GroupResult][GroupResult.java] from `MediaLoaderResult`. Usually, information in `GroupResult` is enough for you to do the job.

## Custom Usage

- **Customize the querying and grouping process**

Use `MediaLoader.create(Provider provider)` to create `MediaLoader` and bind [Querier][MediaQuerier.java] and [Grouper][MediaGrouper.java]  by yourself, for example:

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

- **Add custom group**

Here is an sample that show you how to create groups with custom rule:

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

Then, bind the `MeidaLoader` with `grouper` just like `Customize the querying and grouping process`.

- **Customize the querier**

You can create your own Entity class extends [MediaEntity][MediaEntity.java] and customize the querier by yourself, for example:

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

Well, [DefaultQueryFactory][DefaultQueryFactory.java] may be useful as a reference for you if you find it complex to customize it by yourself.

Then, bind the `MeidaLoader` with `querier` just like `Customize the querying and grouping process`.

For more information, just see my code and annotation please. :)

## Contact Me

**Email :** windy649457889@gmail.com

**QQ :** **2862908118**

## License

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