namespace Rspective.FurryOctoNinja.DataAccess.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class InitialCreate : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Answers",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Text = c.String(maxLength: 200),
                        Question_Id = c.Int(),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Questions", t => t.Question_Id)
                .Index(t => t.Question_Id);
            
            CreateTable(
                "dbo.ApplicationClients",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Name = c.String(nullable: false, maxLength: 50),
                        PublicKey = c.String(nullable: false, maxLength: 200),
                        SecretKey = c.String(nullable: false, maxLength: 200),
                        IsActive = c.Boolean(nullable: true, defaultValue: true),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.ApplicationUsers",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Login = c.String(nullable: false, maxLength: 200),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.Questions",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Text = c.String(maxLength: 200),
                        Survey_Id = c.Int(),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Surveys", t => t.Survey_Id)
                .Index(t => t.Survey_Id);
            
            CreateTable(
                "dbo.Surveys",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Title = c.String(maxLength: 200),
                        Description = c.String(maxLength: 1000),
                        CreatedDate = c.DateTime(),
                    })
                .PrimaryKey(t => t.Id);
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Questions", "Survey_Id", "dbo.Surveys");
            DropForeignKey("dbo.Answers", "Question_Id", "dbo.Questions");
            DropIndex("dbo.Questions", new[] { "Survey_Id" });
            DropIndex("dbo.Answers", new[] { "Question_Id" });
            DropTable("dbo.Surveys");
            DropTable("dbo.Questions");
            DropTable("dbo.ApplicationUsers");
            DropTable("dbo.ApplicationClients");
            DropTable("dbo.Answers");
        }
    }
}
