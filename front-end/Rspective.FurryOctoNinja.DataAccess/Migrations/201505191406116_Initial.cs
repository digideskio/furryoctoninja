namespace Rspective.FurryOctoNinja.DataAccess.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class Initial : DbMigration
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
                        TokenExpirationTime = c.Long(nullable: false),
                        IsActive = c.Boolean(nullable: false),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.ApplicationTokens",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        ClientId = c.Int(nullable: false),
                        UserId = c.Int(nullable: false),
                        Token = c.String(nullable: false),
                        Expiration = c.DateTime(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.ApplicationClients", t => t.ClientId, cascadeDelete: true)
                .ForeignKey("dbo.ApplicationUsers", t => t.UserId, cascadeDelete: true)
                .Index(t => t.ClientId)
                .Index(t => t.UserId);
            
            CreateTable(
                "dbo.ApplicationUsers",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Login = c.String(nullable: false, maxLength: 200),
                        Password = c.String(nullable: false, maxLength: 200),
                        Name = c.String(nullable: false, maxLength: 200),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.ApplicationUserRoles",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Name = c.String(nullable: false),
                        ApplicationUser_Id = c.Int(),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.ApplicationUsers", t => t.ApplicationUser_Id)
                .Index(t => t.ApplicationUser_Id);
            
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
            
            CreateTable(
                "dbo.UserAnswers",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        UserId = c.Int(nullable: false),
                        SurveyId = c.Int(nullable: false),
                        QuestionId = c.Int(nullable: false),
                        AnswerId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Answers", t => t.AnswerId, cascadeDelete: true)
                .ForeignKey("dbo.Questions", t => t.QuestionId, cascadeDelete: true)
                .ForeignKey("dbo.Surveys", t => t.SurveyId, cascadeDelete: true)
                .ForeignKey("dbo.ApplicationUsers", t => t.UserId, cascadeDelete: true)
                .Index(t => t.UserId)
                .Index(t => t.SurveyId)
                .Index(t => t.QuestionId)
                .Index(t => t.AnswerId);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.UserAnswers", "UserId", "dbo.ApplicationUsers");
            DropForeignKey("dbo.UserAnswers", "SurveyId", "dbo.Surveys");
            DropForeignKey("dbo.UserAnswers", "QuestionId", "dbo.Questions");
            DropForeignKey("dbo.UserAnswers", "AnswerId", "dbo.Answers");
            DropForeignKey("dbo.Questions", "Survey_Id", "dbo.Surveys");
            DropForeignKey("dbo.Answers", "Question_Id", "dbo.Questions");
            DropForeignKey("dbo.ApplicationTokens", "UserId", "dbo.ApplicationUsers");
            DropForeignKey("dbo.ApplicationUserRoles", "ApplicationUser_Id", "dbo.ApplicationUsers");
            DropForeignKey("dbo.ApplicationTokens", "ClientId", "dbo.ApplicationClients");
            DropIndex("dbo.UserAnswers", new[] { "AnswerId" });
            DropIndex("dbo.UserAnswers", new[] { "QuestionId" });
            DropIndex("dbo.UserAnswers", new[] { "SurveyId" });
            DropIndex("dbo.UserAnswers", new[] { "UserId" });
            DropIndex("dbo.Questions", new[] { "Survey_Id" });
            DropIndex("dbo.ApplicationUserRoles", new[] { "ApplicationUser_Id" });
            DropIndex("dbo.ApplicationTokens", new[] { "UserId" });
            DropIndex("dbo.ApplicationTokens", new[] { "ClientId" });
            DropIndex("dbo.Answers", new[] { "Question_Id" });
            DropTable("dbo.UserAnswers");
            DropTable("dbo.Surveys");
            DropTable("dbo.Questions");
            DropTable("dbo.ApplicationUserRoles");
            DropTable("dbo.ApplicationUsers");
            DropTable("dbo.ApplicationTokens");
            DropTable("dbo.ApplicationClients");
            DropTable("dbo.Answers");
        }
    }
}
