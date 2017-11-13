package tmall.servlet;

import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.dao.ProductImageDAO;
import tmall.util.ImageUtil;
import tmall.util.Page;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductImageServlet extends BaseBackServlet {
    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        InputStream is = null;
        Map<String, String> params = new HashMap<>();

        //输入流，用来接收浏览器上传的request和params(如：添加分类时的“name 小米手机，图片”等等)，不用request.getParameter获取是因为有图片上传方式为二进制
        is = parseUpload(request, params);

        //根据上传的参数生成productImage对象
        String type = params.get("type");
        int pid = Integer.parseInt(params.get("pid"));
        Product product = productDAO.getProductById(pid);

        ProductImage productImage = new ProductImage();
        productImage.setType(type);
        productImage.setProduct(product);
        productImageDAO.add(productImage);//此处DAO已经为当前productImage设定了id

        //生成文件，若为typeSingle就放在productSingle命名的文件夹下
        String fileName = productImage.getId() + ".jpg";
        String imageFolder = null;
        String imageFolder_small = null;
        String imageFolder_middle = null;
        if (ProductImageDAO.typeSingle.equals(productImage.getType())) {
            imageFolder = request.getSession().getServletContext().getRealPath("img/productSingle");
            imageFolder_small = request.getSession().getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle = request.getSession().getServletContext().getRealPath("img/productSingle_middle");
        } else {
            imageFolder = request.getSession().getServletContext().getRealPath("img/productDetail");
        }
        File file = new File(imageFolder, fileName);
        file.getParentFile().mkdir();
        //复制文件
        try {
            if (is != null && is.available() != 0) {
                try (FileOutputStream fos = new FileOutputStream(file)) {//FileOutputStream服务器持久化
                    byte[] b = new byte[1024 * 1024];
                    int length = 0;
                    while ((length = is.read(b)) != -1) {
                        fos.write(b, 0, length);
                    }
                    fos.flush();
                    //将文件保存为jpg格式
                    BufferedImage bufferedImage = ImageUtil.change2jpg(file);
                    ImageIO.write(bufferedImage, "jpg", file);

                    if (ProductImageDAO.typeSingle.equals(productImage.getType())) {
                        File f_small = new File(imageFolder_small, fileName);
                        f_small.getParentFile().mkdir();
                        ImageUtil.resizeImage(file, 56, 56, f_small);
                        File f_middle = new File(imageFolder_middle, fileName);
                        f_middle.getParentFile().mkdir();
                        ImageUtil.resizeImage(file, 217, 190, f_middle);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "@admin_productImage_list?pid=" + pid;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        ProductImage productImage = productImageDAO.getProductImageById(id);
        productImageDAO.delete(id);

        String imageFolder_single = null;
        String imageFolder_small = null;
        String imageFolder_middle = null;
        String imageFolder_detail = null;
        if (ProductImageDAO.typeSingle.equals(productImage.getType())) {
            imageFolder_single = request.getSession().getServletContext().getRealPath("img/productSingle");
            imageFolder_small = request.getSession().getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle = request.getSession().getServletContext().getRealPath("img/productSingle_middle");

            File f_single = new File(imageFolder_single, productImage.getId() + ".jpg");
            File f_small = new File(imageFolder_small, productImage.getId() + ".jpg");
            File f_middle = new File(imageFolder_middle, productImage.getId() + ".jpg");
            f_single.delete();
            f_small.delete();
            f_middle.delete();

        } else {
            imageFolder_detail = request.getSession().getServletContext().getRealPath("img/productDetail");
            File f_detail = new File(imageFolder_detail, productImage.getId() + ".jpg");
            f_detail.delete();
        }

        return "@admin_productImage_list?pid=" + productImage.getProduct().getId();
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        int pid = Integer.parseInt(request.getParameter("pid"));
        Product product = productDAO.getProductById(pid);
        List<ProductImage> productImageList_single = productImageDAO.list(product, ProductImageDAO.typeSingle);
        List<ProductImage> productImageList_detail = productImageDAO.list(product, ProductImageDAO.typeDetail);

        request.setAttribute("product", product);
        request.setAttribute("productImageList_single", productImageList_single);
        request.setAttribute("productImageList_detail", productImageList_detail);

        return "admin/listProductImage.jsp";
    }
}
