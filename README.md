&#160; &#160; &#160; &#160;列表展示是开发过程中经常用到的功能，通常通过 ListView 或者 RecyclerView 控件来实现。在列表显示的过程中可能会碰到这样的需求：需要对列表进行分组，每个分组都有标题 item view 和内容 item view 而且希望列表在滑动的过程中每个分组的标题 item view 可以一直固定的列表的顶部。之前的博客我们已经通过ListView实现了这一功能，有兴趣的可以参考链接[Android分组悬浮列表实现](http://blog.csdn.net/wuyuxing24/article/details/70477566)。but这一次我们通过 RecyclerView 来实现这一需求。实现过程比 ListView 的实现过程要更加简单。

&#160; &#160; &#160; &#160;在讲怎么实现之前先献上通过RecyclerView实现的效果图
>LinearLayoutManager实现的效果
![pinnedheader_line.gif](http://upload-images.jianshu.io/upload_images/9182331-02fb5cc589cded18.gif?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

>GridLayoutManager实现的效果，要固定在顶部的 item要占据整个一行
![pinnedheader_grid.gif](http://upload-images.jianshu.io/upload_images/9182331-76d50a9dd3cc53b5.gif?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

&#160; &#160; &#160; &#160;接下来就是实现过程了

&#160; &#160; &#160; &#160;我们知道 RecyclerView 给提供了一个RecyclerView.ItemDecoration 来给我们使用，这可是好东西呀。RecyclerView.ItemDecoration 从字面上来看是用来给 RecyclerView 里面每个 item 添加装饰用的(当然也可以给RecyclerView的整体添加装饰)。例如，你可以通过 RecyclerView.ItemDecoration 来给 RecyclerView 的每个 item 添加分割线、给每个 item 添加 padding 等等。这里我们通过 RecyclerView.ItemDecoration 来实现 RecyclerView 分组悬浮列表的功能。

&#160; &#160; &#160; &#160;我们先简单的看下RecyclerView.ItemDecoration里面几个函数：

```
	/**
	 * 可以通过重写这个函数给RecyclerView绘制任意合适的decorations(装饰)
	 * 会在RecyclerView item绘制之前绘制。可以认为是绘制在RecyclerView的下面
	 * 会在RecyclerView类的onDraw()里面调用
	 */
	public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
		onDraw(c, parent);
	}

	/**
	 * deprecated掉的函数我们不管，忽视掉，不建议使用了
	 */
	@Deprecated
	public void onDraw(Canvas c, RecyclerView parent) {
	}

	/**
	 * 可以通过重写这个函数给RecyclerView绘制任意合适的decorations(装饰)
	 * 会在RecyclerView item绘制之后绘制。可以认为是绘制在RecyclerView的上面(在上面在盖一层)
	 * 会在RecyclerView类的super.draw()之后调用,
	 */
	public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
		onDrawOver(c, parent);
	}

	/**
	 * deprecated掉的函数不建议使用了，忽视掉
	 */
	@Deprecated
	public void onDrawOver(Canvas c, RecyclerView parent) {
	}


	/**
	 * deprecated掉的函数不建议使用了，忽视掉
	 */
	@Deprecated
	public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
		outRect.set(0, 0, 0, 0);
	}

	/**
	 * 给RecyclerView　item对应的每个view增加一些offsets(你可以这么认为item对应的view外面还有一层布局，给这个布局增加padding)
	 */
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		getItemOffsets(outRect, ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition(), parent);
	}
```
>看到 ItemDecoration 提供到我们的就三个函数了：onDraw()、onDrawOver()、getItemOffsets()。getItemOffsets()函数会在 RecyclerView 里面每个子 view 测量的时候调用，可以用来给每个子 view 添加offset(间距)。onDraw()会在RecyclerView的onDraw()方法里面调用。onDrawOver()函数会在RecyclerView的draw()函数里面调用。关于onDraw()、onDrawOver()两个函数的区分咱们可以简单的认为onDraw()是在RecyclerView绘制内容的时候调用。onDrawOver()是在RecyclerView绘制完内容之后再调用，相当于可以在RecyclerView之上在绘制一层内容。

&#160; &#160; &#160; &#160;通过对 RecyclerView.ItemDecoration 类的简单分析，再结合我们分组固定标题 View 的需求，我们是要把每个分组的标题 View 固定在顶部，恩，那肯定是在要绘制在RecyclerView层之上的吧，和RecyclerView.ItemDecoration里面的onDrawOver()函数正好对应上了。

&#160; &#160; &#160; &#160;接下来的事情就好办了

&#160; &#160; &#160; &#160;首先，既然有些标题是要固定的，那咱们一定要明确的知道哪些position位置对应的view是标题吧，只能通过adapter做文章了，所有我们就有了一个基础的PinnedHeaderAdapter，代码如下：
```
public abstract class PinnedHeaderAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

	/**
	 * 判断该position对应的位置是要固定
	 *
	 * @param position adapter position
	 * @return true or false
	 */
	public abstract boolean isPinnedPosition(int position);

}
```
&#160; &#160; &#160; &#160;接下来，RecyclerView.ItemDecoration里面的onDrawOver()函数里面我们做好三件事情就好了:第一，找到当前界面要一直固定在顶部的 View、第二，把找到固定在顶部的 View 画在 RecyclerView 的顶部、第三，当将要到达顶部的标题 View 和已经画在顶部的 View 相遇的时候顶部 view 上移的问题。这三个问题实现起来也不复杂，所以这里我们就直接贴代码了，毕竟代码才是王道吗。

```
	/**
	 * 把要固定的View绘制在上层
	 */
	@Override
	public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
		super.onDrawOver(c, parent, state);
		//确保是PinnedHeaderAdapter的adapter,确保有View
		if (parent.getAdapter() instanceof PinnedHeaderAdapter && parent.getChildCount() > 0) {
			PinnedHeaderAdapter adapter = (PinnedHeaderAdapter) parent.getAdapter();
			//找到要固定的pin view
			View firstView = parent.getChildAt(0);
			int firstAdapterPosition = parent.getChildAdapterPosition(firstView);
			int pinnedHeaderPosition = getPinnedHeaderViewPosition(firstAdapterPosition, adapter);
			if (pinnedHeaderPosition != -1) {
				RecyclerView.ViewHolder pinnedHeaderViewHolder = adapter.onCreateViewHolder(parent, adapter.getItemViewType(pinnedHeaderPosition));
				adapter.onBindViewHolder(pinnedHeaderViewHolder, pinnedHeaderPosition);
				//要固定的view
				View pinnedHeaderView = pinnedHeaderViewHolder.itemView;
				ensurePinnedHeaderViewLayout(pinnedHeaderView, parent);
				int sectionPinOffset = 0;
				for (int index = 0; index < parent.getChildCount(); index++) {
					if (adapter.isPinnedPosition(parent.getChildAdapterPosition(parent.getChildAt(index)))) {
						View sectionView = parent.getChildAt(index);
						int sectionTop = sectionView.getTop();
						int pinViewHeight = pinnedHeaderView.getHeight();
						if (sectionTop < pinViewHeight && sectionTop > 0) {
							sectionPinOffset = sectionTop - pinViewHeight;
						}
					}
				}
				int saveCount = c.save();
				c.translate(0, sectionPinOffset);
				c.clipRect(0, 0, parent.getWidth(), pinnedHeaderView.getMeasuredHeight());
				pinnedHeaderView.draw(c);
				c.restoreToCount(saveCount);
			}

		}
	}
```

&#160; &#160; &#160; &#160;整个功能到这就结束了，是不是很简单。最后贡献上源码[RecyclerPinnedHeader源码下载地址](https://github.com/tuacy/RecyclerPinnedHeader)
