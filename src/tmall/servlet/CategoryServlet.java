package tmall.servlet;

import tmall.bean.Category;
import tmall.util.ImageUtil;
import tmall.util.Page;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CategoryServlet实现抽象类BaseBackServlet，并实现其中的add, delete, edit, update, list方法
 */
public class CategoryServlet extends BaseBackServlet {

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        Map<String, String> params = new HashMap<>();
        InputStream is = super.parseUpload(request, params);

        String name = params.get("name");//小米手机
        Category c = new Category();
        c.setName(name);
        categoryDAO.add(c);
        //这里使用getSession(), 如果没有就创建一个
        File imageFolder = new File(request.getSession().getServletContext().getRealPath("img/category"));
        //新建一个命名为id.jpg的文件
        File file = new File(imageFolder, c.getId() + ".jpg");

        try {
            if (is != null && is.available() != 0) {
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    //复制文件，一次复制1M
                    byte b[] = new byte[1024 * 1024];
                    int length = 0;
                    while ((length = is.read(b)) != -1) {
                        fos.write(b, 0, length);
                    }
                    fos.flush();
                    // 将复制好的file转换为jpg文件，仅仅修改文件的后缀名有可能会导致显示异常，因此通过ImageUtil工具类转换成jpg格式
                    BufferedImage img = ImageUtil.change2jpg(file);
                    //仅仅通过ImageIO.write(img, "jpg", file);不足以保证转换出来的jpg文件显示正常
                    ImageIO.write(img, "jpg", file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "@admin_category_list";
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        categoryDAO.delete(id);
        return "@admin_category_list";
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Category c = categoryDAO.getCategoryById(id);
        request.setAttribute("c", c);
        return "admin/editCategory.jsp";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        Map<String, String> params = new HashMap<>();
        InputStream is = super.parseUpload(request, params);

        //System.out.println(params);// {name=平板电视, id=83}
        String name = params.get("name");
        int id = Integer.parseInt(params.get("id"));

        Category c = new Category();
        c.setId(id);
        c.setName(name);
        categoryDAO.update(c);

        File imageFolder = new File(request.getSession().getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, c.getId() + ".jpg");
        file.getParentFile().mkdir();

        try {
            if (is != null && is.available() != 0) {
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    byte b[] = new byte[1024 * 1024];
                    int length = 0;
                    while ((length = is.read(b)) != -1) {
                        fos.write(b, 0, length);
                    }
                    fos.flush();
                    BufferedImage img = ImageUtil.change2jpg(file);
                    ImageIO.write(img, "jpg", file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "@admin_category_list";
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        //通过categoryDAO获取数据集合c_list
        List<Category> c_list = categoryDAO.list(page.getStart(), page.getCount());
        int total = categoryDAO.getTotal();
        page.setTotal(total);
        //通过request.setAttribute放在变量c_list当中，服务器端跳转到jsp后即可获取
        request.setAttribute("c_list", c_list);
        //通过request.setAttribute放在变量page当中，jsp页面通过page.XXX调用
        request.setAttribute("page", page);
        return "admin/listCategory.jsp";
    }
}
