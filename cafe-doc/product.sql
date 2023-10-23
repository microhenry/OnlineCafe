-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: db05
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `productID` int NOT NULL AUTO_INCREMENT,
  `productName` varchar(50) NOT NULL,
  `productDescription` varchar(500) DEFAULT NULL,
  `productCategory` varchar(30) NOT NULL,
  `productPicURL` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`productID`),
  UNIQUE KEY `product_productID_uindex` (`productID`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Pink Drink','A blend of real fruit juice with strawberry and acai fruit flavours, green coffee extract and strawberry inclusions mixed with coconut milk and shaken with ice.','Featured Beverages','Pink Drink'),(2,'Strawberry Acai Lemonade','A blend of real fruit juice with strawberry and acai fruit flavours, green coffee extract and strawberry inclusions mixed with lemonade and shaken with ice.','Featured Beverages','Strawberry Acai Lemonade'),(3,'Flat White','Smooth ristretto shots of espresso get the perfect amount of steamed whole milk to create a not-too-strong, not-too-creamy, just-right flavor.','Barista Favourites','Flat White'),(4,'Cookies and Cream Frappuccino','Frappuccino® Roast coffee, mocha sauce and Frappuccino® chips blended with milk and ice, layered on top of whipped cream and chocolate cookie crumble and topped with vanilla whipped cream, mocha drizzle and even more chocolate cookie crumble. Each sip is as good as the last . . . all the way to the end.','Barista Favourites','Cookies and Cream Frappuccino'),(5,'Vanilla Sweet Cream Cold Brew','Our slow-steeped custom blend of Starbucks® Cold Brew coffee accented with vanilla and topped with a delicate float of house-made vanilla sweet cream that cascades throughout the cup. It\'s over-the-top and super-smooth.','Barista Favourites','Vanilla Sweet Cream Cold Brew'),(6,'White Chocolate Mocha','Our signature espresso meets white chocolate sauce and steamed milk, and then is finished off with sweetened whipped cream to create this supreme white chocolate delight.','Hot Coffees','White Chocolate Mocha'),(7,'Caramel Macchiato','Freshly steamed milk with vanilla-flavored syrup marked with espresso and topped with a caramel drizzle for an oh-so-sweet finish.','Hot Coffees','Caramel Macchiato'),(8,'Cafe Mocha','Our rich, full-bodied espresso combined with bittersweet mocha sauce and steamed milk, then topped with sweetened whipped cream. The classic coffee drink that always sweetly satisfies.','Hot Coffees','Cafe Mocha'),(9,'Cold Brew','Handcrafted in small batches daily, slow-steeped in cool water for 20 hours, without touching heat鈥擲tarbucks庐 Cold Brew is made from our custom blend of beans grown to steep long and cold for a super-smooth flavor.','Cold Coffees','Cold Brew'),(10,'Salted Caramel Cold Brew with Cold Foam','Here\'s a savory-meets-sweet refreshing beverage certain to delight: our signature, super-smooth cold brew, sweetened with a touch of caramel and topped with a salted, rich cold foam.','Cold Coffees','Salted Caramel Cold Brew with Cold Foam'),(11,'Caramel Frappuccino','Caramel syrup meets coffee, milk and ice for a rendezvous in the blender, while whipped cream and buttery caramel sauce layer the love on top. To change things up, try it affogato-style with a hot espresso shot poured right over the top.','Coffee Blended Frappuccino','Caramel Frappuccino'),(12,'Mocha Frappuccino','Mocha sauce, Frappuccino Roast coffee, milk and ice all come together for a mocha flavor that\'ll leave you wanting more. To change things up, try it affogato-style with a hot espresso shot poured right over the top.','Coffee Blended Frappuccino','Mocha Frappuccino'),(13,'White Chocolate Mocha Frappuccino','White chocolate Frappuccino Roast coffee, milk and ice get together for what might be the best thing that happens to you all day. Oh, and there\'s whipped cream on top.','Coffee Blended Frappuccino','White Chocolate Mocha Frappuccino'),(14,'Double Chocolate Chip Frappuccino','Rich mocha-flavored sauce meets up with chocolaty chips, milk and ice for a blender bash. Top it off with sweetened whipped cream and mocha drizzle for a real party in your mouth.','Cream Blended Frappuccino','Double Chocolate Chip Frappuccino'),(15,'Green Tea Cream Frappuccino','This blend of sweetened premium matcha green tea, milk and ice— topped off with sweetened whipped cream—inspires a delicious boost and good green vibes.','Cream Blended Frappuccino','Green Tea Cream Frappuccino'),(16,'Strawberries and Crème Frappuccino','Summer\'s favorite berry is the star of this delicious Frappuccino Blended Beverage—a blend of ice, milk and strawberry puree layered on top of a splash of strawberry puree and finished with vanilla whipped cream.','Cream Blended Frappuccino','Strawberries and Crème Frappuccino'),(17,'English Breakfast','Each sip of this beloved morning black tea unfolds to reveal the complexity of the high-grown full leaves. An elegant, time-honored classic that brings a royal nod to every cup.','Hot Teas','English Breakfast'),(18,'Earl Grey','We take a strong black tea base and add the essence of bergamot, a citrus fruit with subtle lemon and floral lavender notes, to create this aromatically awesome tea flavor.','Hot Teas','Earl Grey'),(19,'Green Tea','This gently smoky, softly sweet green tea is cultivated and hand-plucked high in the mountains of china at 3,500 feet, shrouded in ethereal clouds and mist.','Hot Teas','Green Tea'),(20,'Iced Shaken Lemon Passion Tea','Hibiscus passion tea and sunny lemonade is lightly sweetened and handshaken with ice for a refreshingly vibrant tea.','Iced Teas','Iced Shaken Lemon Passion Tea'),(21,'Iced Shaken Lemon Green Tea','Green tea and sunny lemonade is lightly sweetened and handshaken with ice for a refreshingly vibrant tea.','Iced Teas','Iced Shaken Lemon Green Tea'),(22,'Caramel Signature Hot Chocolate','Steamed milk and mocha sauce are combined with caramel syrups, then topped with sweetened whipped cream.','Other Hot Beverages','Caramel Signature Hot Chocolate'),(23,'Signature Hot Chocolate','Steamed milk and mocha sauce topped with sweetened whipped cream and a chocolate-flavored drizzle. A timeless classic made to sweeten your spirits.','Other Hot Beverages','Signature Hot Chocolate'),(24,'White Hot Chocolate','A traditional hot chocolate beverage made with white chocolate sauce and steamed milk, topped with whipped cream.','Other Hot Beverages','White Hot Chocolate'),(25,'Raspberry Blackcurrent Juice Blend','Refreshing tangy raspberry and blackcurrant juices blended with ice.','Other Cold Beverages','Raspberry Blackcurrent Juice Blend'),(26,'Mango Passionfruit Juice Blend','A tropical mango and passionfruit infusion, perfectly blended with a fruity hibiscus tea and ice.','Other Cold Beverages','Mango Passionfruit Juice Blend');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


