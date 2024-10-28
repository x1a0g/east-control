/*
 Navicat Premium Data Transfer

 Source Server         : east-con
 Source Server Type    : SQLite
 Source Server Version : 3030001
 Source Schema         : main

 Target Server Type    : SQLite
 Target Server Version : 3030001
 File Encoding         : 65001

 Date: 28/10/2024 21:43:41
*/

PRAGMA foreign_keys = false;

-- ----------------------------
-- Table structure for flow
-- ----------------------------
DROP TABLE IF EXISTS "flow";
CREATE TABLE "flow" (
  "id" text NOT NULL,
  "name" text,
  "desc" text,
  "create_time" text
);

-- ----------------------------
-- Records of flow
-- ----------------------------
INSERT INTO "flow" VALUES (1850429354641412096, 'test1', '', '2024-10-27 14:48:32');
INSERT INTO "flow" VALUES (1850439014928871424, 'testxxx', '', '2024-10-27 15:26:55');
INSERT INTO "flow" VALUES (1850503949792792576, 'test2', '', '2024-10-27 19:44:56');

-- ----------------------------
-- Table structure for flow_item
-- ----------------------------
DROP TABLE IF EXISTS "flow_item";
CREATE TABLE "flow_item" (
  "flow_id" text,
  "action_type" integer,
  "x" integer,
  "y" integer,
  "cont" TEXT,
  "create_time" TEXT,
  "id" text
);

-- ----------------------------
-- Records of flow_item
-- ----------------------------
INSERT INTO "flow_item" VALUES (1850429354641412096, 1, 100, 200, NULL, '2024-10-27 14:48:32', 1);
INSERT INTO "flow_item" VALUES (1850439014928871424, 1, NULL, NULL, NULL, '2024-10-27 17:27:22', 1850469326312415232);
INSERT INTO "flow_item" VALUES (1850429354641412096, 3, NULL, NULL, NULL, '2024-10-27 17:27:56', 1850469470890074112);
INSERT INTO "flow_item" VALUES (1850439014928871424, 7, NULL, NULL, NULL, '2024-10-27 18:07:53', 1850479525253259264);
INSERT INTO "flow_item" VALUES (1850439014928871424, 6, 123, 123, NULL, '2024-10-27 18:08:08', 1850479586859196416);
INSERT INTO "flow_item" VALUES (1850429354641412096, 1, NULL, NULL, NULL, '2024-10-27 18:09:19', 1850479884927397888);
INSERT INTO "flow_item" VALUES (1850429354641412096, 1, NULL, NULL, NULL, '2024-10-27 18:09:19', 1850479885661401088);
INSERT INTO "flow_item" VALUES (1850429354641412096, 1, NULL, NULL, NULL, '2024-10-27 18:09:20', 1850479889167839232);
INSERT INTO "flow_item" VALUES (1850503949792792576, 6, 300, 307, NULL, '2024-10-27 20:23:38', 1850513686898835456);
INSERT INTO "flow_item" VALUES (1850503949792792576, 5, NULL, NULL, 100071390038, '2024-10-27 20:23:46', 1850513719941562368);
INSERT INTO "flow_item" VALUES (1850503949792792576, 6, 968, 164, NULL, '2024-10-27 20:24:04', 1850513794998632448);
INSERT INTO "flow_item" VALUES (1850503949792792576, 6, 351, 582, NULL, '2024-10-27 20:24:14', 1850513838858469376);
INSERT INTO "flow_item" VALUES (1850503949792792576, 6, 910, 2281, NULL, '2024-10-27 20:24:26', 1850513887696945152);
INSERT INTO "flow_item" VALUES (1850503949792792576, 6, 547, 2294, NULL, '2024-10-27 20:24:35', 1850513927706411008);
INSERT INTO "flow_item" VALUES (1850429354641412096, 1, NULL, NULL, NULL, '2024-10-27 21:16:17', 1850526936252190720);
INSERT INTO "flow_item" VALUES (1850429354641412096, 1, NULL, NULL, NULL, '2024-10-27 21:16:17', 1850526936810033152);
INSERT INTO "flow_item" VALUES (1850429354641412096, 2, NULL, NULL, NULL, '2024-10-27 21:16:18', 1850526941293744128);
INSERT INTO "flow_item" VALUES (1850429354641412096, 2, NULL, NULL, NULL, '2024-10-27 21:16:18', 1850526941973221376);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS "user";
CREATE TABLE "user" (
  "id" INTEGER NOT NULL,
  "username" TEXT,
  "password" TEXT,
  PRIMARY KEY ("id")
);

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO "user" VALUES (1, 'admin', 'admin');

PRAGMA foreign_keys = true;
