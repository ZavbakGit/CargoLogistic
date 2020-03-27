package `fun`.gladkikh.cargologistic.common.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

abstract class MyListAdapter<T>(val context: Context) : BaseAdapter() {
    var list: List<T> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    abstract fun getLayout(): Int
    abstract fun bindView(item: T,holder:Any)
    abstract fun createViewHolder(view: View) :Any

    override fun getCount() = list.size
    override fun getItem(position: Int) = list[position]
    override fun getItemId(position: Int) = position.toLong()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val viewHolder: Any

        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(getLayout(), parent, false)
            viewHolder = createViewHolder(convertView)
            convertView!!.tag = viewHolder
        } else {
            viewHolder = convertView.tag
        }
        bindView(list[position],viewHolder)
        return convertView
    }

}