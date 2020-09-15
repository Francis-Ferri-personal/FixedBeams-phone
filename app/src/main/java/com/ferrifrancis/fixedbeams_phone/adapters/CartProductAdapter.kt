import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ferrifrancis.fixedbeams_phone.R
import com.ferrifrancis.fixedbeams_phone.data.product.ProductModelClass
import com.ferrifrancis.fixedbeams_phone.util.SharedPreferencesManager
import kotlinx.android.synthetic.main.layout_shopping_cart_list_item.view.*


class CartProductAdapter(
    val products: ArrayList<ProductModelClass>
): RecyclerView.Adapter<CartProductAdapter.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartProductAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_shopping_cart_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: CartProductAdapter.ViewHolder, position: Int) {
        holder.bindItems(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bindItems(product: ProductModelClass){
            val textViewProductName = itemView.findViewById<TextView>(R.id.textView_productName)
            val textViewProductPrice = itemView.findViewById<TextView>(R.id.textView_price)
            val imageViewProduct = itemView.findViewById<ImageView>(R.id.imageViewProductImage)
            val textViewQuantity = itemView.findViewById<TextView>(R.id.textView_quantity)

            Glide.with(itemView).load(product.srcImage)
                .centerInside()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageViewProduct)

            textViewProductName.text = "${product.name}"
            textViewProductPrice.text = "Price (c/u): $ ${product.price}"
            textViewQuantity.text = product.quantity.toString()
            addButtonListeners(itemView,product)
        }
        fun addButtonListeners(itemView: View, product: ProductModelClass){
            var tempCounter = itemView.findViewById<TextView>(R.id.textView_quantity).text.toString().toInt()
            itemView.plus_button.setOnClickListener {
                tempCounter += 1
                itemView.textView_quantity.text = tempCounter.toString()
                product.quantity = tempCounter
                SharedPreferencesManager.saveProduct(product, itemView.context, "CarritoAdaptador")
            }
            itemView.minus_button.setOnClickListener {
                tempCounter -= 1
                itemView.textView_quantity.text = tempCounter.toString()
                product.quantity = tempCounter
                SharedPreferencesManager.saveProduct(product, itemView.context, "CarritoAdaptador")
            }
        }
    }
}