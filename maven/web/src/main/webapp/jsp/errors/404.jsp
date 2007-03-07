<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page isErrorPage="true" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%--
need to pass to header.jspf the following params:
1. title
2. additional css to use (if any)
--%>


<%@ include file="/jsp/fragments/header.jspf" %>
<%@ include file="/jsp/fragments/nav.jspf" %>    

<div id="main">
    <div>
        <h1>A Website About Ernst Jünger</h1>
        <p>
            Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Morbi malesuada, ligula venenatis interdum placerat,
            ligula pede varius diam, nec tincidunt ligula ligula eget felis. Nunc eget mauris. Sed vitae magna. Nam rutrum auctor erat.
            Suspendisse et est ac enim placerat accumsan. Morbi vestibulum orci ut sapien. Proin neque tellus, gravida vestibulum,
            ultrices ac, condimentum quis, velit. Etiam et pede. Cras fermentum.
            Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae;
            Mauris sodales porta dui. Vivamus egestas velit vel metus gravida hendrerit. Curabitur sodales felis ut felis.
            Nulla nonummy, enim ut tristique interdum, odio elit dapibus sem, sed dictum leo orci sed quam. In pulvinar lacinia neque.
            Donec ut elit. Quisque purus.
        </p>
        <p>
            Ut dapibus condimentum libero. Nulla aliquam. Cras venenatis augue porta nisl. Ut ac mi nec nibh euismod tempus.
            Sed et ante. Quisque pellentesque pretium mauris. In congue nisi quis velit. Sed mollis.
            Pellentesque velit nulla, sagittis a, elementum et, sagittis quis, felis. Fusce nec lorem quis turpis rutrum bibendum.
            Nunc magna eros, pulvinar nec, dictum et, lobortis eu, felis. Praesent sit amet ligula. Praesent a ligula vitae pede imperdiet viverra.
            In iaculis viverra mauris. In iaculis nibh eget nisi. Sed tincidunt nisi in lacus. Ut nonummy. Aenean ut orci vitae magna rhoncus pellentesque.
        </p>
        <h2>Jenseits von Gut und Böse</h2>
        <p>
            Praesent vulputate justo placerat sapien condimentum molestie. Mauris nec augue ut nibh luctus tincidunt. 
            Nam tincidunt dapibus lectus. Donec elementum porttitor velit. Duis non ipsum. Donec eget nulla.
            Donec ullamcorper. Vestibulum varius congue enim. Vestibulum ornare odio at neque.
            Ut facilisis sollicitudin purus. Nam placerat. Nulla dui odio, egestas vitae, pulvinar et, blandit in, lorem.
        </p>
        <p>
            Nulla commodo risus id sem imperdiet scelerisque. Cum sociis natoque penatibus et magnis dis parturient montes,
            nascetur ridiculus mus. Morbi eu purus. Ut sem. Ut vitae tortor. Nullam non massa at ipsum semper semper. 
            Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Curabitur id turpis. 
            Vivamus odio. Nulla sit amet justo sollicitudin magna aliquam faucibus. Donec tempor. 
            Fusce dapibus elementum lacus. In hac habitasse platea dictumst. Proin id sem.
            Aliquam erat volutpat. Nunc sit amet metus quis nunc ultricies consectetuer.
        </p>
    </div>
</div>


<%@ include file="/jsp/fragments/extra.jspf" %>    
<%@ include file="/jsp/fragments/footer.jspf" %>