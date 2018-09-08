package ru.fantlab.android.ui.adapter.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import ru.fantlab.android.R
import ru.fantlab.android.data.dao.model.ClassParent
import ru.fantlab.android.ui.widgets.treeview.TreeNode
import ru.fantlab.android.ui.widgets.treeview.TreeViewAdapter
import ru.fantlab.android.ui.widgets.treeview.TreeViewBinder

class ClassParentViewHolder : TreeViewBinder<ClassParentViewHolder.ViewHolder>() {

	override val layoutId: Int
		get() = R.layout.work_classif_parent_row_item

	override fun provideViewHolder(itemView: View): ViewHolder {
		return ViewHolder(itemView)
	}

	override fun bindView(
			holder: RecyclerView.ViewHolder, position: Int, node: TreeNode<*>, onTreeNodeListener: TreeViewAdapter.OnTreeNodeListener?
	) {
		(holder as ClassParentViewHolder.ViewHolder).ivArrow.rotation = 0f
		holder.ivArrow.setImageResource(R.drawable.ic_keyboard_arrow_right_black_18dp)
		val rotateDegree = if (node.isExpand) 90f else 0f
		holder.ivArrow.rotation = rotateDegree
		val parentNode = node.content as ClassParent?
		holder.tvName.text = parentNode!!.title
		if (node.isLeaf)
			holder.ivArrow.visibility = View.INVISIBLE
		else
			holder.ivArrow.visibility = View.VISIBLE
	}

	class ViewHolder(rootView: View) : TreeViewBinder.ViewHolder(rootView) {
		val ivArrow: ImageView = rootView.findViewById<View>(R.id.iv_arrow) as ImageView
		var tvName: TextView = rootView.findViewById<View>(R.id.tv_name) as TextView
	}
}